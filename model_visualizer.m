%% Setup

graph = readmatrix("src/weights.csv");
pad_bottom = zeros([1 17]);
pad_side = zeros([18 1]);
names = readlines("data/names.txt");

adjusted_bottom = [graph ; pad_bottom];
adjusted = [adjusted_bottom pad_side];

%% Connection Weights
figure(1);
pcolor(adjusted);

colorbar;
axis square;
set(gca, 'xtick', (1:numel(names)) + .25, 'xticklabels', names);
set(gca, 'ytick', (1:numel(names)) + 0.5, 'yticklabels', names);
set(gca, 'fontsize', 14);
xtickangle(60);
title("Connection Weight Strengths");

%% Achievement and Priority
x_A = 0:.01:5;
x_P = 1:.01:5;
k = 2.56;
A = 1 ./ (16 * k).^x_A;
P = 1 ./ (sqrt(x_P) .* (1 - 1 ./ (16.^x_P)).^x_P);

figure(2);
hold on;
pA = plot(x_A, A);
pP = plot(x_P, P);
pA.LineWidth = 2;
pP.LineWidth = 2;

set(gca, 'fontsize', 14);
set(gca, 'LineWidth', 2);
set(gca, 'xtick', 1:5);
grid on;
title("Achievement and Priority Decay With Layer Depth");
xlabel("Layer Depth");
ylabel("Value");

legend("Achievement Score", "Priority Score");