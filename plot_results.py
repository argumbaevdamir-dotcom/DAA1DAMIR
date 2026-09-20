
from pathlib import Path
import csv
import math
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator

ROOT = Path(__file__).resolve().parent
with (ROOT / 'results.csv').open(newline='') as f:
    rows = list(csv.DictReader(f))

algorithms = ['MergeSort', 'QuickSort', 'QuickSelect']
inputs = ['random', 'sorted', 'duplicates']
assert len(rows) == 36
assert len({(r['algorithm'], r['input'], r['n']) for r in rows}) == 36


colors = ['#00E5FF', '#FF007F', '#00FF66']
markers = ['o', 's', '^']

BG_DARK = '#0D0F12'
AXIS_BG = '#14181F'
TEXT_COLOR = '#E0E6ED'

plt.rcParams.update({
    'font.size': 11,
    'font.family': 'monospace',
    'figure.facecolor': BG_DARK,
    'axes.facecolor': AXIS_BG,
    'text.color': TEXT_COLOR,
    'axes.labelcolor': TEXT_COLOR,
    'xtick.color': TEXT_COLOR,
    'ytick.color': TEXT_COLOR,
    'axes.edgecolor': '#2A3342',
    'axes.spines.top': False,
    'axes.spines.right': False,
    'savefig.dpi': 200
})

(ROOT / 'plots').mkdir(exist_ok=True)

for metric, title, ylabel, subtitle in [
    ('time', 'Execution time vs input size', 'Time (ms, log scale)',
     'Logarithmic axes; each point is the supplied measured time. QuickSelect selects one element.'),
    ('depth', 'Maximum call depth vs input size', 'Maximum depth',
     'Depth uses the implementation counter; iterative QuickSelect records depth 1.'),
    ('ratio', 'Normalized comparison counts', 'Normalized comparisons',
     'MergeSort / QuickSort: comparisons / (n log₂ n)    |    QuickSelect: comparisons / n'),
]:
    fig, axes = plt.subplots(1, 3, figsize=(13.8, 4.8), sharey=True)

    for ax, input_kind in zip(axes, inputs):
        for algorithm, color, marker in zip(algorithms, colors, markers):
            subset = sorted((r for r in rows if r['algorithm'] == algorithm and r['input'] == input_kind), key=lambda r: int(r['n']))
            ns = [int(r['n']) for r in subset]
            if metric == 'time':
                values = [float(r['time_ms']) for r in subset]
            elif metric == 'depth':
                values = [int(r['max_depth']) for r in subset]
            else:
                values = [int(r['comparisons']) / (n if algorithm == 'QuickSelect' else n * math.log2(n)) for r, n in zip(subset, ns)]


            ax.plot(ns, values, color=color, lw=5, alpha=0.25)

            ax.plot(ns, values, label=algorithm, color=color, marker=marker,
                    lw=2, ms=6, mec=BG_DARK, mew=1.2)

        ax.set_xscale('log')
        if metric == 'time':
            ax.set_yscale('log')
        else:
            ax.set_ylim(bottom=0)

        if metric == 'depth':
            ax.yaxis.set_major_locator(MaxNLocator(integer=True))

        ax.set_xticks([1000, 10000, 100000, 1000000], ['1k', '10k', '100k', '1M'])


        ax.set_title(input_kind.capitalize(), color='#00E5FF', weight='bold', pad=10)
        ax.set_xlabel('Input size n', labelpad=8)
        ax.grid(True, which='major', color='#2A3342', linestyle='--', linewidth=0.8, alpha=0.6)

    axes[0].set_ylabel(ylabel)


    fig.suptitle(title, fontsize=17, weight='bold', y=0.99, color='#FFFFFF')
    fig.text(0.5, 0.905, subtitle, ha='center', fontsize=9.5, color='#8B9BB4')


    handles, labels = axes[0].get_legend_handles_labels()

    handles, labels = handles[1::2], labels[1::2]

    legend = fig.legend(handles, labels, loc='lower center', ncol=3, frameon=True,
                        facecolor='#14181F', edgecolor='#2A3342', bbox_to_anchor=(0.5, 0.005))
    for text in legend.get_texts():
        text.set_color(TEXT_COLOR)

    fig.subplots_adjust(top=0.78, bottom=0.19, left=0.075, right=0.985, wspace=0.14)

    fig.savefig(ROOT / 'plots' / f'{metric}_vs_n.png', facecolor=fig.get_facecolor(), edgecolor='none')
    plt.close(fig)

print('Validated 36 distinct rows; created 3 cyberpunk-style plots.')
