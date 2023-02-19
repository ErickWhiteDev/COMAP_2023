initial_achievements = readmatrix("initial_achievements.csv"); % Unmodified initial achievement scores
achievements = readmatrix("achievements.csv"); % Initial achievement scores after achievement propagation
modified_achievements = readmatrix("modified_achievements.csv"); % Achievement scores after multipliers and completions

concat_achievements = [initial_achievements ; achievements ; modified_achievements]';
%{
Row 1 : unmodified initial achievement scores
Row 2 : initial achievement scores after achievement propagation
Row 3 : initial achievement scores after effect multipliers
Rows 4-20 : initial achievement scores after setting one goal to complete
%}

priorities = readmatrix("priorities.csv");

names = readlines("names.txt");

figure(1);
bar(concat_achievements(:,[1 ; 2]));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);
ylim([0 1]);

title("Original Achievement Scores vs. Achievement Scores After Achievement Propagation");
xlabel("Goals");
ylabel("Achievement Scores");
legend("Original Scores", "Scores After Achievement Propagation");

figure(2);
bar(priorities(1,:));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);

title("Prioritization Scores for Goals");
xlabel("Goals");
ylabel("Prioritization Score");

figure(3);
bar(concat_achievements(:,[2 ; 3]));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);
ylim([0 1]);

title("Original Achievement Scores vs. Achievement Scores After External Effects");
xlabel("Goals");
ylabel("Achievement Scores");
legend("Original Scores", "Scores After External Effects");

% for i = 4:20
%     figure(i);
%     bar(concat_achievements(:,[2 ; i]));
%     set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
%     xtickangle(60);
%     
%     title(sprintf('Original Achievement Scores vs. Achievement Scores After "%s" Goal Accomplished', names(i - 3)));
%     xlabel("Goals");
%     ylabel("Achievement Scores");
%     legend("Original Scores", sprintf('Scores After "%s" Goal Accomplished', names(i - 3)));
% end