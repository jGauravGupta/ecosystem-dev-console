let securityAnnotations = {};
function fetchSecurityAnnotations() {
    fetch('resources/dev/security-audit')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                securityAnnotations = data;
                renderSecurityAnnotations();
            })
            .catch(error => {
                console.error('Error fetching security annotations:', error);
            });
}

function renderSecurityAnnotations() {
    const tbody = document.getElementById('SecurityAnnotationsTable').querySelector('tbody');
    tbody.innerHTML = '';

    // The data is a Map<Object, Set<Annotation>> serialized as JSON
    // We will iterate over the keys and annotations and display them
    for (const key in securityAnnotations) {
        if (securityAnnotations.hasOwnProperty(key)) {
            const annotations = securityAnnotations[key];
            const annotationList = Array.isArray(annotations) ? annotations : [];
            const annotationsStr = annotationList.map(a => {
                if (typeof a === 'object' && a !== null) {
                    // Serialize annotation object in a readable string
                    return a.type || JSON.stringify(a);
                } else {
                    return a.toString();
                }
            }).join(', ');

            const tr = document.createElement('tr');
            tr.innerHTML = `
                                    <td>${annotations.key}</td>
                                    <td>${annotations.annotationClassNames}</td>
                                `;
            tbody.appendChild(tr);
        }
    }
}

function exportSecurityAnnotationsCSV() {
    const rows = [];
    const tbody = document.getElementById('SecurityAnnotationsTable').querySelector('tbody');
    if (!tbody) {
        alert('No data to export.')
        return;
    }
    const trs = tbody.querySelectorAll('tr');
    trs.forEach(tr => {
        const cells = Array.from(tr.children).map(td => td.textContent.trim());
        rows.push(cells);
    });
    if (rows.length === 0) {
        alert('No data to export.')
        return;
    }
    let csvContent = rows.map(row => row.map(value => {
            if (value.includes(',') || value.includes('"') || value.includes('\n')) {
                return '"' + value.replace(/"/g, '""') + '"';
            }
            return value;
        }).join(',')).join('\n');
    const blob = new Blob([csvContent], {type: 'text/csv;charset=utf-8;'});
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.setAttribute('href', url);
    link.setAttribute('download', 'security_annotations.csv');
    link.style.visibility = 'hidden';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

function copySecurityAnnotationsToClipboard() {
    const tbody = document.getElementById('SecurityAnnotationsTable').querySelector('tbody');
    if (!tbody) {
        alert('No data to copy.')
        return;
    }
    const trs = tbody.querySelectorAll('tr');
    if (trs.length === 0) {
        alert('No data to copy.')
        return;
    }
    let text = '';
    trs.forEach(tr => {
        const cells = Array.from(tr.children).map(td => td.textContent.trim());
        text += cells.join('\t') + '\n';
    });
    navigator.clipboard.writeText(text).then(() => {
        alert('Copied to clipboard');
    }).catch(() => {
        alert('Failed to copy');
    });
}