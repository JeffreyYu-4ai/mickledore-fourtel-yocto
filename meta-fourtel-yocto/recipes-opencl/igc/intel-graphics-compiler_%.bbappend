# Append -Wno-deprecated-declarations to CXXFLAGS
# This suppresses deprecated declarations warnings

# Ensure that CXXFLAGS is appended after other possible modifications
CXXFLAGS += " -Wno-deprecated-declarations"

# Alternatively, if you want to ensure it's added only once and in the right place, you can use:
# EXTRA_OECXXFLAGS += " -Wno-deprecated-declarations"

# If the recipe uses a different variable for C++ flags, adjust accordingly.

