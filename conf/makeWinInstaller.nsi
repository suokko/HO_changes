!ifndef BUILDDIR
	!warning "BUILDDIR not defined, using 'build' as default"
	!define BUILDDIR build
!endif

!ifndef OUTFILE
	!error "OUTFILE not defined"
!else
	Outfile ${OUTFILE}
!endif

!ifndef HOVERSION
	!error "HOVERSION not defined"
!endif

InstallDir "$PROGRAMFILES\HattrickOrganizer"
Name "Hattrick Organizer"
RequestExecutionLevel admin
VIProductVersion "$HOVERSION"

!include MUI2.nsh

!define MUI_ABORTWARNING

!define MUI_LICENSEPAGE_CHECKBOX
!insertmacro MUI_PAGE_LICENSE "${BUILDDIR}/HO_lgpl.txt"

!define MUI_COMPONENTSPAGE_NODESC
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES

!define MUI_UNCONFIRMPAGE_TEXT_TOP "WARNING!$\r$\n$\r$\nThis will uninstall HO completely, INCLUDING YOUR DATABASE!"
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

!insertmacro MUI_LANGUAGE "English"

!ifdef WITHJRE
	!ifndef JREFILE
		!error "Using WITHJRE, but JREFILE is not defined!"
	!endif
	!ifndef JREPATH
		!error "Using WITHJRE, but JREPATH is not defined!"
	!endif
!endif

!ifdef WITHJRE	
InstType "Full (with Java Runtime Environment)"
InstType "Minimal"

Section "Java Runtime Environment ${WITHJRE}" SEC_JRE
	SectionIn 1 
	SetOutPath "$INSTDIR"
	File "${JREPATH}\${JREFILE}"
	ExecWait '$INSTDIR\${JREFILE}'
	Delete "$INSTDIR\${JREFILE}"
SectionEnd
!endif

Section "Hattrick Organizer ${HOVERSION}" SEC_HO
    SectionIn 1 2 RO 
	SetOutPath "$INSTDIR"
	File /r "${BUILDDIR}\*.*"
	WriteUninstaller "$INSTDIR\Uninstall.exe"
SectionEnd

Section "Create Shortcut on Desktop" SEC_DESKTOP
    SectionIn 1 2 
	SetShellVarContext all
	CreateShortCut "$DESKTOP\HattrickOrganizer.lnk" "$INSTDIR\HO.bat" "" "$INSTDIR\Logo.ico"
SectionEnd

Section "Create Shortcut in Startmenu" SEC_STARTMENU
    SectionIn 1 2 
	SetShellVarContext all
	CreateDirectory  "$SMPROGRAMS\HattrickOrganizer"
	CreateShortCut "$SMPROGRAMS\HattrickOrganizer\HattrickOrganizer.lnk" "$INSTDIR\HO.bat" "" "$INSTDIR\Logo.ico"
SectionEnd

Section Uninstall SEC_UNINST
	RMDir /r "$INSTDIR"
	SetShellVarContext all
	RMDir /r "$SMPROGRAMS\HattrickOrganizer"
	Delete "$DESKTOP\HattrickOrganizer.lnk"
SectionEnd