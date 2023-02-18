initial_achievements = readmatrix("initial_achievements.csv");
achievements = readmatrix("achievements.csv");
concat_achievements = [initial_achievements ; achievements]';

priorities = readmatrix("priorities.csv");

names = readlines("names.txt");

figure(1);
bar(concat_achievements(:,[1 ; 2]));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names);
xtickangle(60);

xlabel("Goals");
ylabel("Achievement Metric");
legend('Original Values', 'After Achievement Propagation');

figure(2);
bar(priorities(1,:));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names);
xtickangle(60);

xlabel("Goals");
ylabel("Prioritization Score");