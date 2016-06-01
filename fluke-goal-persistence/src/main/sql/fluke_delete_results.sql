-- This SQL script deletes all result related date from a fluke database
DELETE FROM game_result;
DELETE FROM game;
UPDATE player SET conceded_goals=0, scored_defensive_goals=0, scored_offensive_goals=0, scored_total_goals=0, won_games=0, lost_games=0;
UPDATE team SET conceded_goals=0,  lost_games=0, scored_defensive_goals=0, scored_offensive_goals=0, scored_total_goals=0, won_games=0;