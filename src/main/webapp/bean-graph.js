function renderBeanGraph(graphData) {
    const nodes = [];
    const edges = [];

    Object.values(graphData.nodes).forEach(node => {
        nodes.push({
            id: node.beanId,
            label: node.beanType,
            title: node.description,
            shape: 'box',
            color: 'rgba(0, 44, 62)',
            font: { color: '#fff' }
        });
        node.dependencies.forEach(dep => edges.push({ from: node.beanId, to: dep.beanId, arrows: 'to' }));
    });

    const container = document.getElementById('graphContainer');
    const data = { nodes: new vis.DataSet(nodes), edges: new vis.DataSet(edges) };
    const options = {
        layout: { hierarchical: { direction: 'UD', sortMethod: 'directed' } },
        edges: { smooth: true, arrows: { to: { enabled: true, scaleFactor: 1 } } },
        interaction: { hover: true, navigationButtons: true, keyboard: true },
        physics: { enabled: false }
    };

    new vis.Network(container, data, options);
}

document.addEventListener('DOMContentLoaded', () => {
    fetch('resources/dev/bean-graph')
        .then(response => response.json())
        .then(renderBeanGraph)
        .catch(err => console.error('Failed to load bean dependency graph:', err));
});
