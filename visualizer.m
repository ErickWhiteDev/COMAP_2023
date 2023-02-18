initial_achievements = readmatrix("initial_achievements.csv");
modified_achievements = readmatrix("modified_achievements.csv");
achievements = [initial_achievements ; modified_achievements]';

names = readlines("names.txt");

bar(achievements(:,[1 ; 2]));

set(gca, 'xtick', 1:numel(achievements(:,1)), 'xticklabels', names);
xtickangle(60);

xlabel("Goals");
ylabel("Achievement Metric");
legend('Current Values', 'No Poverty Accomplished');