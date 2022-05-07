package space_resistance.game;

import space_resistance.settings.GameMode;
import space_resistance.settings.MultiplayerMode;

public record GameConfig(MultiplayerMode multiplayerMode, GameMode gameMode) {}

