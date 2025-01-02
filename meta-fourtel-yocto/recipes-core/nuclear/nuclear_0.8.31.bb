# meta-fourtel-yocto/recipes-core/nuclear/nuclear_%.bb

SUMMARY = "NUClear is a powerful C++ framework for building concurrent and distributed systems."
DESCRIPTION = "NUClear is a high-performance C++ framework that simplifies the development of concurrent and distributed systems. It leverages modern C++ features to provide a flexible and efficient environment for building scalable applications."

HOMEPAGE = "https://github.com/Fastcode/NUClear"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "https://github.com/Fastcode/NUClear/archive/e71efa58334a8c7f1accaf85243136f79882f4d7.tar.gz;downloadfilename=NUClear-e71efa58334a8c7f1accaf85243136f79882f4d7.tar.gz \
           "
SRC_URI[sha256sum] = "d54f7ee73a1b9617749b993c79c3ff06b101ff9b20424eab15f4cad403891674"

SRCREV = "e71efa58334a8c7f1accaf85243136f79882f4d7"  
# Commit hash from the tarball

S = "${WORKDIR}/NUClear-e71efa58334a8c7f1accaf85243136f79882f4d7"

# Inherit CMake and other necessary classes
inherit cmake pkgconfig

# Specify runtime dependencies
DEPENDS = "cmake boost spdlog"

# Additional CMake configuration flags
EXTRA_OECMAKE += "\
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_INSTALL_PREFIX=${prefix} \
    -DBUILD_TESTS=OFF \
    -DBUILD_SHARED_LIBS=OFF \
    -DCMAKE_PREFIX_PATH=${STAGING_DIR_TARGET}/usr \
    "

# Optional: Apply patches if necessary
#FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
#SRC_URI += "file://nuclear_patch.patch"

# Specify the version
PV = "0.8.31"

# Optional: Adjust compiler flags or other variables as needed

