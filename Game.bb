; ~ The main file of the game
;----------------------------------------------------------------------------------------------------------------------------------------------------
; ~ SCP - Containment Breach Ultimate Edition Reborn v1.6
;----------------------------------------------------------------------------------------------------------------------------------------------------
; ~ This is a modification of the game "SCP - Containment Breach"
; ~ The mod is developed by the "Ultimate Edition Team" (https://www.moddb.com/company/ultimate-edition-team)
; ~ It is released under the CC-BY-SA 3.0 license as it is a derivative work based on SCP - Containment Breach and the SCP Foundation
;----------------------------------------------------------------------------------------------------------------------------------------------------
; ~ Contact us: https://discord.gg/KnGVpTRN6a
;----------------------------------------------------------------------------------------------------------------------------------------------------

Global ErrorMessageInitialized% = False

Const VersionNumber$ = "1.6"

InitErrorMsgs(13, True)

Function UpdateErrorMessages%()
	SetErrorMsg(0, Format(GetLocalString("error", "title"), VersionNumber))
	SetErrorMsg(1, GetLocalString("error", "shot")) 
	SetErrorMsg(2, "---------------------------------------------------")
	SetErrorMsg(3, "Date and time: " + CurrentDate() + ", " + CurrentTime())
	SetErrorMsg(4, "OS: " + SystemProperty("os") + " " + (32 + (GetEnv("ProgramFiles(X86)") <> 0) * 32) + " Bit (Build: " + SystemProperty("osbuild") + ")")
	SetErrorMsg(5, "CPU: " + Trim(SystemProperty("cpuname")) + " (Arch: " + SystemProperty("cpuarch") + ", " + GetEnv("NUMBER_OF_PROCESSORS") + " Threads)")
	
	ErrorMessageInitialized = True
End Function

Global AppDataPath$ = GetEnv("AppData")

If FileType(AppDataPath + "\scpcb-ue\") <> 2 Then CreateDir(AppDataPath + "\scpcb-ue")
If FileType(AppDataPath + "\scpcb-ue\Data\") <> 2 Then CreateDir(AppDataPath + "\scpcb-ue\Data")

Include "Source Code\KeyBinds_Core.bb"
Include "Source Code\INI_Core.bb"
Include "Source Code\Strict_Functions_Core.bb"

LoadOptionsINI()

Const LanguageFile$ = "Data\local.ini"
Const SubtitlesFile$ = "Data\subtitles.jsonc"
Const AchievementsFile$ = "Data\achievements.jsonc"
Const LoadingScreensFile$ = "Data\loading_screens.jsonc"
Const SCP294File$ = "Data\SCP-294.jsonc"
Const FontsFile$ = "Data\fonts.ini"
Const SCP1499ChunksFile$ = "Data\1499chunks.jsonc"

IniWriteBuffer(LanguageFile)
IniWriteBuffer(FontsFile)

Include "Source Code\Launcher_Core.bb"
Include "Source Code\Main_Core.bb"
