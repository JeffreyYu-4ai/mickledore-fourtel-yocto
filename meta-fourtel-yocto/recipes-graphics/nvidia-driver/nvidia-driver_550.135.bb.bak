SUMMARY = "NVIDIA Proprietary Driver 550.135"
DESCRIPTION = "Installs the NVIDIA proprietary driver version 550.135 from a local runfile."
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0"  
# Placeholder since the license is proprietary

SRC_URI = "file://NVIDIA-Linux-x86_64-550.135.run"

S = "${WORKDIR}"

# Inherit the 'runscript' class to handle execution of the runfile
#inherit runscript

# Inherit the 'allarch' class for architecture-independent packages
inherit allarch

# Define a variable to specify the installation directory
INSTALL_DIR = "${D}${prefix}/nvidia"


DEPENDS += "virtual/kernel kmod-native"

# Define runtime dependencies
RDEPENDS_${PN} = "libglvnd mesa-gl gtk2 wayland openssl1.1"

# Ensure that the kernel headers are available
DEPENDS += "virtual/kernel"

# Prevent the recipe from packaging anything
PACKAGES = "${PN}"

# Specify that this recipe should not be included in any package
FILES_${PN} += "/usr/lib/nvidia/* /var/lib/nvidia/*"
 
# Define the do_install task
do_install() {
    # Prepend kmod-native's bin directory to PATH to ensure modprobe is found
    export PATH="${STAGING_BINDIR_NATIVE}:${PATH}"

    # Make the installer executable
    chmod +x ${WORKDIR}/NVIDIA-Linux-x86_64-550.135.run

    # Create a temporary extraction directory within WORKDIR
    INSTALL_TMP_DIR="${WORKDIR}/install_tmp"
    mkdir -p ${INSTALL_TMP_DIR}

    # Run the installer in extract-only mode to avoid executing scripts that write to host
    ${WORKDIR}/NVIDIA-Linux-x86_64-550.135.run --extract-only --target=${INSTALL_TMP_DIR}

    # Ensure the installer does not attempt to create /var/lib/nvidia on the host
    # Pre-create the /var/lib/nvidia directory within the temporary install directory
    mkdir -p ${INSTALL_TMP_DIR}/var/lib/nvidia

    # Copy the extracted files to the staging directory
    # Adjust the path based on the actual extracted directory structure
    # Assuming extraction creates a directory like NVIDIA-Linux-x86_64-550.135
    EXTRACTED_DIR="${INSTALL_TMP_DIR}/NVIDIA-Linux-x86_64-550.135"

    # Verify that the extracted directory exists
    if [ -d "${EXTRACTED_DIR}" ]; then
        # Copy all files except for /var/lib/nvidia to the prefix directory
        find "${EXTRACTED_DIR}" -mindepth 1 -maxdepth 1 ! -path "${EXTRACTED_DIR}/var/lib/nvidia" -exec cp -r {} ${D}${prefix}/ \;

        # Copy the /var/lib/nvidia directory separately
        cp -r "${EXTRACTED_DIR}/var/lib/nvidia" ${D}/var/lib/
    else
        bbnote "Extraction directory ${EXTRACTED_DIR} does not exist."
    fi
}

# Override do_compile to do nothing since we're using a runfile
do_compile() {
    :
}

# Prevent packaging as a binary
#do_package() {
#    :
#}

# Prevent the recipe from deploying anything
do_deploy() {
    :
}

