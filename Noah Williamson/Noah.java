 /*Safer Sephiroths AI script from FF7
AI: Setup
{
  Turn off Death Handling for Safer*Sephiroth
  TempVar:CharLv99 = GlobalVar:CharLv99
  TempVar:BzHeadDeaths = GlobalVar:BzHeadDeaths
  If (GlobalVar:JenovaKoR == 1) Then
  {
    TempVar:JenovaBonus = 80000
  } Else {
    TempVar:JenovaBonus = 0
  }
  TempVar:Stat = 320000
  TempVar:Stat = TempVar:Stat - 30000 * (8 - TempVar:CharLv99)
  Safer*Sephiroth's Max HP = TempVar:Stat + TempVar:JenovaBonus
  Safer*Sephiroth's HP = Safer*Sephiroth's Max HP - (TempVar:BzHeadDeaths * 100)
  Safer*Sephiroth's Att = Safer*Sephiroth's Att + 2 * TempVar:CharLv99
  Safer*Sephiroth's Def = Safer*Sephiroth's Def + 20 * TempVar:CharLv99
  Safer*Sephiroth's MAt = Safer*Sephiroth's MAt + 5 * TempVar:CharLv99
  Safer*Sephiroth's MDf = Safer*Sephiroth's MDf + 16 * TempVar:CharLv99
  Choose Self
  Use <x> on Target
  Use <Appear> on Target
}
AI: Main
{  
  Safer*Sephiroth's MP = Safer*Sephiroth's Max MP
  Count = Count + 1
  If (Count == 1) Then
  {
    If (Self has Slow Status) Then
    {
      Choose Self
      Cast DeSpell on Target
    } Else If (TempVar:MoveSet == 1) Then {
      Choose All Opponents
      Cast DeSpell on Target
      TempVar:MoveSet = 0
    } Else {
      Choose Self
      Cast Wall on Target
      TempVar:MoveSet = 1
    }
  } Else If (Count == 2) Then {
    If (TempVar:MoveSet == 0) Then {
      Choose All Opponents
      Use Deen on Target
    } Else {
      Choose Random Opponent
      Use Shadow Flare on Target
    }
  } Else If (Count == 3) Then {
    Choose Random Opponent with Highest HP
    Use < > (Physical Attack) on Target
  } Else If (Count == 4) Then {
    Choose Self
    Use <> (Fly Up) on Target
    Stage = 1
    Safer*Sephiroth's IdleAnim = Flying High
    Safer*Sephiroth's HurtAnim = Flinch (Flying High)
    Safer*Sephiroth's Range = 16
  } Else If (Count == 5) Then {
    Choose Random Opponent
    Use Pale Horse on Target
  } Else If (Count == 6) Then {
    Choose All Opponents
    Use Super Nova on Target
  } Else If (Count == 7) Then {
    If (Safer*Sephiroth's HP > 25% of Safer*Sephiroth's Max HP) Then
    {
      Choose Random Opponent
      Use Break on Target
    } Else {
      Choose Random Opponent
      Use Heartless Angel on Target
    }
  } Else {
    Choose Self
    Use <> (Fly Down) on Target
    Stage = 0
    Safer*Sephiroth's IdleAnim = Flying Low
    Safer*Sephiroth's HurtAnim = Flinch (Flying Low)
    Safer*Sephiroth's Range = 1
    Count = 0
  }
}
AI: Counter - Death
{
  Choose Self
  If (Stage == 0) Then
  {
    Use <> on Target
  } Else
  {
    Use <> on Target
  }
}
AI: Counter - 13
{
  Remove Self
}
*/