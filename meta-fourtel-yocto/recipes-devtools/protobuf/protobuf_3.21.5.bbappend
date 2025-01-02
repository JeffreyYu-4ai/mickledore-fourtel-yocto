# meta-fourtel-yocto/recipes-devtools/protobuf/protobuf_3.21.5.bbappend

# Ensure that the layer's files directory is searched first
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Append additional CMake configuration flags
EXTRA_OECMAKE += " \
    -DWITH_PROTOC=\"${STAGING_BINDIR_NATIVE}/protoc\" \
    -Dprotobuf_BUILD_TESTS=OFF \
    -Dprotobuf_BUILD_LIBPROTOC=ON \
    -DBUILD_SHARED_LIBS=OFF \
    -DZLIB_ROOT=${STAGING_DIR_TARGET}/usr \
"

# Optionally, disable building tests
# This is already handled by the flag above, but ensure no other flags enable tests
PROVIDES += "protobuf"

# If necessary, adjust dependencies
# DEPENDS += "zlib-native"

# Optional: Specify the path to protoc if different
# Since protoc is built as a native package in Yocto, use the correct path

