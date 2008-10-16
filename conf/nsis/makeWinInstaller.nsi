############## Compiler Checks ############## 

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

!ifndef LOCALLANGPATH
	!error "LOCALLANGPATH not defined"
!endif

!ifdef WITHJRE
	!ifndef JREFILE
		!error "Using WITHJRE, but JREFILE is not defined!"
	!endif
	!ifndef JREPATH
		!error "Using WITHJRE, but JREPATH is not defined!"
	!endif
!endif

!define HOREGKEY "Software\HattrickOrganizer"


################## General ################## 

InstallDir "$PROGRAMFILES\HattrickOrganizer"
;Get installation folder from registry if available
InstallDirRegKey HKLM ${HOREGKEY} "InstallLocation"

Name "Hattrick Organizer"
RequestExecutionLevel admin
VIProductVersion "$HOVERSION"


############### Use Modern UI ############### 

!include MUI2.nsh


############ Interface settings #############

!define MUI_ABORTWARNING


##### Remember the installer language #######

!define MUI_LANGDLL_REGISTRY_ROOT HKLM
!define MUI_LANGDLL_REGISTRY_KEY ${HOREGKEY} 
!define MUI_LANGDLL_REGISTRY_VALUENAME "Installer Language"


################### Pages ###################

!insertmacro MUI_PAGE_WELCOME
!define MUI_LICENSEPAGE_CHECKBOX
!insertmacro MUI_PAGE_LICENSE "${BUILDDIR}/HO_lgpl.txt"

; Show componentspage only WITHJRE
!define MUI_COMPONENTSPAGE_NODESC
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!define MUI_FINISHPAGE_RUN "$INSTDIR\HO.bat"
!define MUI_FINISHPAGE_SHOWREADME "$INSTDIR\changelog.txt"
!define MUI_FINISHPAGE_LINK "Hattrick Organizer Homepage"
!define MUI_FINISHPAGE_LINK_LOCATION "http://www.hattrickorganizer.net"

!insertmacro MUI_PAGE_FINISH

!insertmacro MUI_UNPAGE_WELCOME
!define MUI_UNCONFIRMPAGE_TEXT_TOP $(DB_WILL_BE_REMOVED)
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_UNPAGE_FINISH

############## Language Macros ##############

!macro LANG_LOAD LANGLOAD
	!insertmacro MUI_LANGUAGE "${LANGLOAD}"
	!verbose off
	!include "${LOCALLANGPATH}/${LANGLOAD}.nsh"
	!verbose on
	!ifdef THISLANG
		  !undef THISLANG
	!endif
!macroend
 
!macro LANG_STRING NAME VALUE
  LangString "${NAME}" "${LANG_${THISLANG}}" "${VALUE}"
!macroend
 
!macro LANG_UNSTRING NAME VALUE
  !insertmacro LANG_STRING "un.${NAME}" "${VALUE}"
!macroend

################# Languages #################

!insertmacro LANG_LOAD "English" ;first language is the default language
!insertmacro LANG_LOAD "Afrikaans"
!insertmacro LANG_LOAD "Albanian"
!insertmacro LANG_LOAD "Arabic"
!insertmacro LANG_LOAD "Basque"
!insertmacro LANG_LOAD "Belarusian"
!insertmacro LANG_LOAD "Bosnian"
!insertmacro LANG_LOAD "Breton"
!insertmacro LANG_LOAD "Bulgarian"
!insertmacro LANG_LOAD "Catalan"
!insertmacro LANG_LOAD "Croatian"
!insertmacro LANG_LOAD "Czech"
!insertmacro LANG_LOAD "Danish"
!insertmacro LANG_LOAD "Dutch"
!insertmacro LANG_LOAD "Estonian"
!insertmacro LANG_LOAD "Farsi"
!insertmacro LANG_LOAD "Finnish"
!insertmacro LANG_LOAD "French"
!insertmacro LANG_LOAD "Galician"
!insertmacro LANG_LOAD "German"
!insertmacro LANG_LOAD "Greek"
!insertmacro LANG_LOAD "Hebrew"
!insertmacro LANG_LOAD "Hungarian"
!insertmacro LANG_LOAD "Icelandic"
!insertmacro LANG_LOAD "Indonesian"
!insertmacro LANG_LOAD "Irish"
!insertmacro LANG_LOAD "Italian"
!insertmacro LANG_LOAD "Japanese"
!insertmacro LANG_LOAD "Korean"
!insertmacro LANG_LOAD "Kurdish"
!insertmacro LANG_LOAD "Latvian"
!insertmacro LANG_LOAD "Lithuanian"
!insertmacro LANG_LOAD "Luxembourgish"
!insertmacro LANG_LOAD "Macedonian"
!insertmacro LANG_LOAD "Malay"
!insertmacro LANG_LOAD "Mongolian"
!insertmacro LANG_LOAD "Norwegian"
!insertmacro LANG_LOAD "NorwegianNynorsk"
!insertmacro LANG_LOAD "Polish"
!insertmacro LANG_LOAD "PortugueseBR"
!insertmacro LANG_LOAD "Portuguese"
!insertmacro LANG_LOAD "Romanian"
!insertmacro LANG_LOAD "Russian"
!insertmacro LANG_LOAD "SerbianLatin"
!insertmacro LANG_LOAD "Serbian"
!insertmacro LANG_LOAD "SimpChinese"
!insertmacro LANG_LOAD "Slovak"
!insertmacro LANG_LOAD "Slovenian"
!insertmacro LANG_LOAD "SpanishInternational"
!insertmacro LANG_LOAD "Spanish"
!insertmacro LANG_LOAD "Swedish"
!insertmacro LANG_LOAD "Thai"
!insertmacro LANG_LOAD "TradChinese"
!insertmacro LANG_LOAD "Turkish"
!insertmacro LANG_LOAD "Ukrainian"
!insertmacro LANG_LOAD "Uzbek"
!insertmacro LANG_LOAD "Welsh"


############### Reserve Files ###############
;If you are using solid compression, files that are required before
;the actual installation should be stored first in the data block,
;because this will make your installer start faster.
  
!insertmacro MUI_RESERVEFILE_LANGDLL

####### Installer Functions/Sections ########

Function .onInit
  !insertmacro MUI_LANGDLL_DISPLAY
FunctionEnd

!ifdef WITHJRE	
	InstType "Full (with Java Runtime Environment)"
!else
	InstType "Full"
!endif
InstType "Minimal"

!ifdef WITHJRE	
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
	WriteRegStr HKLM ${HOREGKEY} "InstallLocation" $INSTDIR
	SetOutPath "$INSTDIR"
	File /r "${BUILDDIR}\*.*"
	WriteUninstaller "$INSTDIR\Uninstall.exe"
SectionEnd

Section "$(CREATE_DESKTOP)" SEC_DESKTOP
    SectionIn 1
	SetShellVarContext all
    CreateShortCut "$DESKTOP\Hattrick Organizer.lnk" "$INSTDIR\HO.bat" "" "$INSTDIR\Logo.ico"
SectionEnd

Section "$(CREATE_STARTMENU)" SEC_STARTMENU
    SectionIn 1
	SetShellVarContext all
    CreateDirectory "$SMPROGRAMS\Hattrick Organizer"
    CreateShortCut "$SMPROGRAMS\Hattrick Organizer\Hattrick Organizer.lnk" "$INSTDIR\HO.bat" "" "$INSTDIR\Logo.ico"
	CreateShortCut "$SMPROGRAMS\Hattrick Organizer\Uninstall.lnk" "$INSTDIR\Uninstall.exe"
SectionEnd

###### Uninstaller Functions/Sections #######

Function un.onInit
  !insertmacro MUI_UNGETLANGUAGE
FunctionEnd

Section Uninstall SEC_UNINST
	MessageBox MB_YESNO|MB_DEFBUTTON2 "$(UNINST_CONFIRM)" /SD IDYES IDYES uninstConfirmed IDNO abortUninst
	abortUninst:
	quit
	uninstConfirmed:
	RMDir /r "$INSTDIR"
	SetShellVarContext all
	RMDir /r "$SMPROGRAMS\Hattrick Organizer"
	Delete "$DESKTOP\Hattrick Organizer.lnk"
	DeleteRegKey HKLM ${HOREGKEY}
SectionEnd