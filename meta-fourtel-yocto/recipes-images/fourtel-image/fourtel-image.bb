SUMMARY = "Fourtel Linux Development Image"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://MIT;md5=1eff1f3abb5819f6dc9cf11d4e592292"

SRC_URI += "file://isolinux/isolinux.cfg"

inherit core-image

# Add base tools and libraries similar to the Docker environment
IMAGE_INSTALL += " \
    networkmanager \
    wpa-supplicant \
    dhcpcd \
    net-tools \
    grub-efi \
    dialog \
    linux-firmware \
    vim \
    nano \
    systemd \
    openssh \
    ethtool \
    usbutils \
    pciutils \
    util-linux \
    bash \
    python3 \
    python3-pip \
    git \
    cmake \
    ninja \
    gdb \
    nano \
    rsync \
    openssh \
    ccache \
    wget \
    parallel-deqp-runner \
    jq \
    openssl \
    ncurses \
    zlib \
    env-vars \
    shadow \
    sudo-user \
    wget \
    libx11 \
    libxcomposite \
    libxi \
    libxtst \
    nss \
    nspr \
    libxrandr \
    atk \
    at-spi2-atk \
    unzip \
    xorriso \
    python3-magic \
    python3-xxhash \
    python3-pylint \
    python3-termcolor \
    python3-pillow \
    python3-protobuf \
    python3-tqdm \
    python3-pytest \
    python3-gcovr \
    "


#remove pango cairo libx11  vim
#missing ...
#python3-stringcase python3-pyproj python3-SQLAlchemy python3-pyogrio python3-geopandas

# libcups libxss gtk3 gdk-pixbuf2

#IMAGE_INSTALL += "nodejs"
inherit extrausers

EXTRA_USERS_PARAMS = " \
    groupadd -r fourtel; \
    useradd -m -r -g fourtel fourtel; \
"
#IMAGE_INSTALL:append = " toolchain-setup"

#IMAGE_INSTALL:append = " custom-python-setup "

# Add WiFi drivers (adjust based on your hardware)
#IMAGE_INSTALL:append = " firmware-iwlwifi "

RDEPENDS += "nuclear"


# Enable systemd
DISTRO_FEATURES:append = " systemd"

# Set the default init system
VIRTUAL-RUNTIME_init_manager = "systemd"

# Enable serial console
SERIAL_CONSOLE = "115200 ttyS0"

# Configure network settings
NETWORK_MANAGER_ENABLE = "1"

# Enable ISO image
IMAGE_FSTYPES += "iso"

# Optional: Set ISO label
DISTRO_FEATURES:append = " iso9660"
