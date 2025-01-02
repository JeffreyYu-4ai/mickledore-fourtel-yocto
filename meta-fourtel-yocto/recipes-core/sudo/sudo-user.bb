SUMMARY = "Sudo user configuration"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://MIT;md5=1eff1f3abb5819f6dc9cf11d4e592292"

SRC_URI = "file://user \
           file://MIT \
          "
S = "${WORKDIR}"



do_install() {
    install -d ${D}/etc/sudoers.d
    install -m 0440 ${WORKDIR}/user ${D}/etc/sudoers.d/user
}

FILES_${PN} += "/etc/sudoers.d/user"

RDEPENDS_${PN} = "sudo"


