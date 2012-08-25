# Simple makefile to update JNI module to get windows application data path
# To cross compile JNI developer needs
# * Windows Java Development Kit for headers (in Wine for testing)
# * mingw32 cross compiler (packages: gcc-mingw-w64 and mingw-w64-dev)
# * GNU make
#
# With those tools simple running make produces windows JNI modules to
# be loaded by Hattrick Organizer.
#
# Modules are included in version control to keep Windows build as simple as
# possible. Building JNI modules require quite a lot native development tools
# that might be missing from developers system.

# The path to JDK installation
JPATH?=/usr/lib/jvm/java-6-openjdk-amd64
JPATH_WIN?=${HOME}/.wine/drive_c/Program\ Files/Java/jdk1.7.0_06

#Windows cross-compiler name
CC_WIN?=i686-w64-mingw32-gcc
CC_WIN64?=x86_64-w64-mingw32-gcc

#compiler flags
CFLAGS=-shared -g -Wall -Wextra -Werror -O2 -I.
CFLAGS_WIN=-Xlinker --add-stdcall-alias

# Libraries to generate. Unix shared objects are only for testing
LIBS=Shell32Wrapper.dll \
	Shell32Wrapper64.dll \
#	libShell32Wrapper64.so \
#	libShell32Wrapper.so \
#

# Target directory for libraries
TARGET=src/conf/addToZip/

#Path to native interface class
HOFILE_CLASS=bin/ho/core/util/HOFile.class
HOFILE_JAVA=src/java/ho/core/util/HOFile.java

#Native header
NATIVE_H=ho_core_util_Win32ShellWrapper.h
NATIVE_C=src/jni/Win32ShellWrapper.c

.PHONY: ${HOFILE_CLASS}

all: ${addprefix ${TARGET},${LIBS}}
	ant -buildfile AntBuild.xml copy2build

# Compile java to byte code
${HOFILE_CLASS}: ${HOFILE_JAVA}
	ant -buildfile AntBuild.xml ant-compile

# Generate native header
${NATIVE_H}: ${HOFILE_CLASS} Makefile
	javah -classpath bin/ -jni ho.core.util.Win32ShellWrapper

# Build native libraries
${TARGET}libShell32Wrapper64.so: ${NATIVE_C} ${NATIVE_H}
	${CC} -fPIC ${CFLAGS} -m64 -I${JPATH}/include $< -o $@

${TARGET}libShell32Wrapper.so: ${NATIVE_C} ${NATIVE_H}
	${CC} -fPIC ${CFLAGS} -m32 -I${JPATH}/include $< -o $@

${TARGET}Shell32Wrapper64.dll: ${NATIVE_C} ${NATIVE_H}
	${CC_WIN64} ${CFLAGS} ${CFLAGS_WIN} -I${JPATH_WIN}/include -I${JPATH_WIN}/include/win32 $< -o $@

src/conf/addToZip/Shell32Wrapper.dll: ${NATIVE_C} ${NATIVE_H}
	${CC_WIN} ${CFLAGS} ${CFLAGS_WIN} -I${JPATH_WIN}/include -I${JPATH_WIN}/include/win32 $< -o $@
