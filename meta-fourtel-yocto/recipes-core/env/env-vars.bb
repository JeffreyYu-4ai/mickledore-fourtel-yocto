SUMMARY = "Environment Variables for Fourtel Linux"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://MIT;md5=1eff1f3abb5819f6dc9cf11d4e592292"

SRC_URI = "file://env.sh \
	   file://MIT \	
          "

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/profile.d
    install -m 0644 env.sh ${D}/etc/profile.d/
}

FILES_${PN} += "/etc/profile.d/*"

