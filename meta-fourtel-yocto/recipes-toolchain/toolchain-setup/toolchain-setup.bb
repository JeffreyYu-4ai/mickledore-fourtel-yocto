SUMMARY = "Toolchain Setup and Configuration"
DESCRIPTION = "Sets up Python paths, linker configurations, and installs helper scripts for the toolchain."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Specify the version
PV = "1.0"

# Specify where to find the source files
SRC_URI = " \
    file://etc/xdg/pip/pip.conf \
    file://etc/ld.so.conf.d/usrlocal.conf \
    file://usr/local/bin/install-from-source.py \
    file://usr/local/bin/generate_generic_toolchain.py \
"

# Inherit necessary classes
inherit allarch

# Define dependencies
DEPENDS = "python3"

# Installation Directory
PREFIX = "/usr/local"

# Declare that this recipe provides virtual/toolchain-setup
PROVIDES = "virtual/toolchain-setup"

do_install() {
    # Create necessary directories
    install -d ${D}${PREFIX}/bin
    install -d ${D}${sysconfdir}/xdg/pip
    install -d ${D}${sysconfdir}/ld.so.conf.d
    install -d ${D}${PREFIX}/toolchain

    # Copy pip configuration
    install -m 0644 ${WORKDIR}/etc/xdg/pip/pip.conf ${D}${sysconfdir}/xdg/pip/pip.conf

    # Copy ld.so configuration
    install -m 0644 ${WORKDIR}/etc/ld.so.conf.d/usrlocal.conf ${D}${sysconfdir}/ld.so.conf.d/usrlocal.conf

    # Install helper scripts
    install -m 0755 ${WORKDIR}/usr/local/bin/install-from-source.py ${D}${PREFIX}/bin/install-from-source.py
    ln -sf install-from-source.py ${D}${PREFIX}/bin/install-from-source

    install -m 0755 ${WORKDIR}/usr/local/bin/generate_generic_toolchain.py ${D}${PREFIX}/toolchain/generate_generic_toolchain.py

    # Create symlink /usr/local/lib64 -> /usr/local/lib
    ln -sf lib ${D}${PREFIX}/lib64

    # Debug: List contents of /usr/local/bin and /usr/local/toolchain
    echo "Listing /usr/local/bin:"
    ls -l ${D}${PREFIX}/bin/

    echo "Listing /usr/local/toolchain:"
    ls -l ${D}${PREFIX}/toolchain/
}

do_install:append() {
    # Define the Python version
    PYTHON_VERSION="3.10"

    # Define the local site-packages directory
    LOCAL_SITEPACKAGES_DIR="/usr/local/lib/python${PYTHON_VERSION}/dist-packages"

    # Ensure the local site-packages directory exists
    install -d ${D}${LOCAL_SITEPACKAGES_DIR}

    # Create the local.pth file to include /usr/local in Python's site-packages
    echo "${LOCAL_SITEPACKAGES_DIR}" > ${D}${LOCAL_SITEPACKAGES_DIR}/local.pth
}

# Define the Python function for the generate_toolchain task
python do_generate_toolchain() {
    import subprocess
    import os

    # Define the paths
    toolchain_dir = os.path.join(d.getVar('D'), d.getVar('PREFIX'), 'toolchain')
    generate_script = os.path.join(toolchain_dir, 'generate_generic_toolchain.py')
    prefix = '/usr'

    # Ensure the script exists
    if not os.path.exists(generate_script):
        bb.fatal("generate_generic_toolchain.py not found at %s" % generate_script)

    # Execute the script
    cmd = ['python3', generate_script, '--prefix', prefix]
    try:
        subprocess.check_call(cmd)
    except subprocess.CalledProcessError as e:
        bb.fatal("Error executing generate_generic_toolchain.py: %s" % e)
}

# Add the do_generate_toolchain task after do_install and before do_package
addtask do_generate_toolchain after do_install before do_package

# Assign the Python function to the do_generate_toolchain task
do_generate_toolchain[nostamp] = "1"

# Explicitly list all installed files (avoid listing entire directories)
FILES_${PN} += " \
    /usr/local/bin/install-from-source.py \
    /usr/local/bin/install-from-source \
    /usr/local/toolchain/generate_generic_toolchain.py \
    /usr/local/lib/python3.10/dist-packages/local.pth \
    /etc/xdg/pip/pip.conf \
    /etc/ld.so.conf.d/usrlocal.conf \
"

# Specify runtime dependencies
RDEPENDS_${PN} += "python3"

# Skip ldconfig QA check as Yocto handles it
INSANE_SKIP_${PN} += "ldconfig"

