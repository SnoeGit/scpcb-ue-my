; ~ Subtitles IDs constants
;[Block]
Const ANNOUNCEMENT% = 0
Const FIRST_PERSON% = 1
Const SECOND_PERSON% = 2
Const THIRD_PERSON% = 3
;[End Block]

Type Subtitles
	Field Txt$[4]
	Field Timer#[4]
End Type

Function UpdateSubtitles()
	Local sub.Subtitles, i%
	Local ShouldDeleteSubtitles% = True
	
	; ~ TODO: Fix subtitles if two sounds played at the same time
	For sub.Subtitles = Each Subtitles
		If sub.Subtitles = First Subtitles Then
			For i = ANNOUNCEMENT To THIRD_PERSON
				If sub\Timer[i] > 0.0 Then
					ShouldDeleteSubtitles = False
					sub\Timer[i] = sub\Timer[i] - fps\FPSFactor[0 + (SelectedDoor <> Null)]
				EndIf
			Next
			If ShouldDeleteSubtitles Then Delete(sub)
		EndIf
	Next
End Function

Function RenderSubtitles()
	Local sub.Subtitles, i%
	
	For sub.Subtitles = Each Subtitles
		If sub.Subtitles = First Subtitles Then
			For i = ANNOUNCEMENT To THIRD_PERSON
				If sub\Timer[i] > 0.0 Then
					SetFont(fo\FontID[Font_Default])
					Color(0, 0, 0)
					Text(mo\Viewport_Center_X + MenuScale, mo\Viewport_Center_Y + (291 * MenuScale) - ((i * 20) * MenuScale), sub\Txt[i], True, False)
					Color(opt\SubColorR, opt\SubColorG, opt\SubColorB)
					Text(mo\Viewport_Center_X, mo\Viewport_Center_Y + (290 * MenuScale) - ((i * 20) * MenuScale), sub\Txt[i], True, False)
				EndIf
			Next
		EndIf
	Next
End Function

Const SubtitlesFile$ = "Data\subtitles.ini"

Global SubtitlesID%

Function ShowSubtitles(Name$)
	CatchErrors("Uncaught (ShowSubtitles)")
	
	Local Loc% = GetINISectionLocation(SubtitlesFile, Name)
	Local Person% = GetINIString2(SubtitlesFile, Loc, "Person")
	Local LinesAmount% = GetINIInt2(SubtitlesFile, Loc, "LinesAmount")
	Local i%, sub.Subtitles
	
	Select Person
		Case 1
			;[Block]
			SubtitlesID = FIRST_PERSON
			;[End Block]
		Case 2
			;[Block]
			SubtitlesID = SECOND_PERSON
			;[End Block]
		Case 3
			SubtitlesID = THIRD_PERSON
			;[End Block]
		Default
			;[Block]
			SubtitlesID = ANNOUNCEMENT
			;[End Block]
	End Select
	
	ClearSubtitles(SubtitlesID)
	
	For i = 1 To LinesAmount
		sub.Subtitles = New Subtitles
		sub\Txt[SubtitlesID] = GetINIString2(SubtitlesFile, Loc, "Txt" + i)
		sub\Timer[SubtitlesID] = 70.0 * GetINIFloat2(SubtitlesFile, Loc, "Timer" + i)
	Next
	
	CatchErrors("ShowSubtitles")
End Function

Function ClearSubtitles(SubID%)
	If (Not opt\EnableSubtitles) Then Return
	
	Local sub.Subtitles
	
	For sub.Subtitles = Each Subtitles
		If sub\Txt[SubID] <> "" Then sub\Txt[SubID] = ""
		If sub\Timer[SubID] <> 0.0 Then sub\Timer[SubID] = 0.0
	Next
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D