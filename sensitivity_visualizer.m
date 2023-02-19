%% Setup
base_achievements = readmatrix("data/achievements.csv"); % 1.0x propagated achievement scores (control)
half_achievements = readmatrix("data/achievements_half.csv"); % 0.5x propagated achievement scores
three_halves_achievements = readmatrix("data/achievements_three_halves.csv"); % 1.5x propagated achievement scores

concat_achievements = [half_achievements ; base_achievements ; three_halves_achievements]';
slope = concat_achievements(:,3) - concat_achievements(:,1); % Sensitivity to achievement propagation

names = readlines("names.txt");
multiplier_names = readlines("multiplier_names.txt");

priorities = readmatrix("data/priorities.csv");

%% Sensitivity Analysis on Achievement Score Propagation
figure(1);
bar(concat_achievements(:,[1 ; 2 ; 3]));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);
ylim([0 1]);

title("Sensitivity Analysis on Achievement Score Propagation With 0.5x, 1.0x, and 1.5x Weights");
xlabel("Goals");
ylabel("Achievement Scores");
legend("0.5x Weights Sensitivity", "1.0x Weights Sensitivity (Control)", "1.5x Weights Sensitivity");

%% Correlation Between Sensitivity and Priority
figure(2);
yyaxis left;
bar(priorities(1,:));
ylabel("Prioritization Score");
yyaxis right;
p = plot(slope);
p.LineWidth = 2;
ylim([0 0.1]);
ylabel("Achievement Score Sensitivity");

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);

title("Correlation Between Sensitivity and Priority");
xlabel("Goals");