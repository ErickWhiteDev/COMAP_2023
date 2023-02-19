%% Setup
initial_achievements = readmatrix("data/initial_achievements.csv"); % Unmodified initial achievement scores
achievements = readmatrix("data/achievements.csv"); % Initial achievement scores after achievement propagation
modified_achievements = readmatrix("data/modified_achievements.csv"); % Achievement scores after multipliers and completions

concat_achievements = [initial_achievements ; achievements ; modified_achievements]';
%{
Row 1 : unmodified initial achievement scores
Row 2 : initial achievement scores after achievement propagation
Row 3 : propagated achievement scores after all effect multipliers
Rows 4 - 8 : propagated achievement scores after one effect multiplier
Rows 9 - 25 : initial achievement scores after setting one goal to complete
%}

priorities = readmatrix("data/priorities.csv");

names = readlines("names.txt");
multiplier_names = readlines("multiplier_names.txt");

%% Original Achievement Scores vs. Achievement Scores After Achievement Propagation
figure(1);
bar(concat_achievements(:,[1 ; 2]));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);
ylim([0 1]);

title("Original Achievement Scores vs. Achievement Scores After Achievement Propagation");
xlabel("Goals");
ylabel("Achievement Scores");
legend("Original Scores", "Scores After Achievement Propagation");

%% Prioritization Scores for Goals
figure(2);
bar(priorities(1,:));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);

title("Prioritization Scores for Goals");
xlabel("Goals");
ylabel("Prioritization Score");

%% Propagated Achievement Scores vs. Achievement Scores After All External Effects
figure(3);
bar(concat_achievements(:,[2 ; 3]));

set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);
ylim([0 1]);

title("Propagated Achievement Scores vs. Propagated Achievement Scores After All External Effects");
xlabel("Goals");
ylabel("Achievement Scores");
legend("Before External Effects", "After External Effects");

%% Propagated Achievement Scores vs. Achievement Scores With Individual Multipliers
for i = 4:8
    figure(i);
    bar(concat_achievements(:,[2 ; i]));
    set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
    xtickangle(60);
    
    title(sprintf('Propagated Achievement Scores vs. Propagated Achievement Scores After "%s" Multiplier', multiplier_names(i - 3)));
    xlabel("Goals");
    ylabel("Achievement Scores");
    legend("Propagated Scores", sprintf('Propagated Scores After "%s" Multiplier', multiplier_names(i - 3)));
end

%% Original Achievement Scores vs. Achievement Scores After No Poverty Goal Accomplished
figure(9);
bar(concat_achievements(:,[1 ; 9]));
set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);

title("Original Achievement Scores vs. Achievement Scores After No Poverty Goal Accomplished");
xlabel("Goals");
ylabel("Achievement Scores");
legend("Original Scores", "Scores After No Poverty Goal Accomplished");

%% Original Achievement Scores vs. Achievement Scores After Responsible Production and Consumption Goal Accomplished
figure(20);
bar(concat_achievements(:,[1 ; 20]));
set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
xtickangle(60);

title("Original Achievement Scores vs. Achievement Scores After Responsible Production and Consumption Goal Accomplished");
xlabel("Goals");
ylabel("Achievement Scores");
legend("Original Scores", "Scores After Responsible Production and Consumption Goal Accomplished");

%% Uncomment to generate all comparison graphs
% for i = 4:20
%     figure(i);
%     bar(concat_achievements(:,[1 ; i]));
%     set(gca, 'xtick', 1:numel(concat_achievements(:,1)), 'xticklabels', names, 'fontsize', 14);
%     xtickangle(60);
%     
%     title(sprintf('Original Achievement Scores vs. Achievement Scores After "%s" Goal Accomplished', names(i - 3)));
%     xlabel("Goals");
%     ylabel("Achievement Scores");
%     legend("Original Scores", sprintf('Scores After "%s" Goal Accomplished', names(i - 3)));
% end