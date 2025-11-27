function escapeCsv(value) {
  if (typeof value !== 'string') return value;
  if (value.includes(',') || value.includes('"') || value.includes('\n')) {
    return '"' + value.replace(/"/g, '""') + '"';
  }
  return value;
}

function exportCSV(type) {
  const table = document.getElementById(type + 'Table');
  if (!table) return alert('No data to export in this tab.');
  const trs = table.querySelectorAll('tbody tr');
  if (!trs.length) return alert('No data to export in this tab.');

  const rows = Array.from(trs).map(tr => Array.from(tr.children).map(td => escapeCsv(td.textContent.trim())));
  const blob = new Blob([rows.map(r => r.join(',')).join('\n')], { type: 'text/csv;charset=utf-8;' });
  const url = URL.createObjectURL(blob);

  const link = Object.assign(document.createElement('a'), {
    href: url,
    download: `${type}_beans.csv`,
    style: 'visibility:hidden'
  });

  document.body.append(link);
  link.click();
  document.body.removeChild(link);
}

function copyToClipboard(type) {
  const table = document.getElementById(type + 'Table');
  if (!table) return alert('No data to copy in this tab.');
  const trs = table.querySelectorAll('tbody tr');
  if (!trs.length) return alert('No data to copy in this tab.');

  const text = Array.from(trs)
    .map(tr => Array.from(tr.children).map(td => td.textContent.trim()).join('\t'))
    .join('\n');

  navigator.clipboard.writeText(text)
    .then(() => alert('Copied to clipboard'))
    .catch(() => alert('Failed to copy'));
}

function highlight(text, filter) {
  if (!filter) return text;
  const regex = new RegExp(`(${filter.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`, 'gi');
  return text.replace(regex, '<span class="highlight">$1</span>');
}

function saveState() {
    localStorage.setItem('searchFilter', document.getElementById('searchInput').value);
    localStorage.setItem('sortOrder', JSON.stringify(currentSortOrder));
    localStorage.setItem('sortColumn', JSON.stringify(currentSortColumn));
}
function restoreState() {
    const activeTab = localStorage.getItem('activeTab');
    const searchFilter = localStorage.getItem('searchFilter') || '';
    const sortOrder = JSON.parse(localStorage.getItem('sortOrder') || '{}');
    const sortColumn = JSON.parse(localStorage.getItem('sortColumn') || '{}');

    const searchInput = document.getElementById('searchInput');

    if (searchInput) {
        searchInput.value = searchFilter;
    }
    currentSortOrder = sortOrder;
    currentSortColumn = sortColumn;

    const iframe = document.getElementById('dashboardIframe');

    if (activeTab) {
        // If there was a previously selected tab, activate it (this will hide the iframe)
        const sideButton = document.getElementById('side-' + activeTab);
        if (sideButton) {
            sideButton.click();
        } else {
            // fallback to first side button
            const firstBtn = document.querySelector('.sideButton');
            if (firstBtn) firstBtn.click();
        }
    } else {
        // No active tab saved — show the dashboard iframe by default
        if (iframe) iframe.style.display = 'block';
        // hide the global search input when showing dashboard
        if (searchInput) searchInput.style.display = 'none';
        // ensure no tab content is visible
        const tabcontent = document.getElementsByClassName('tabcontent');
        for (const tab of tabcontent) {
            tab.style.display = 'none';
            tab.setAttribute('aria-hidden', 'true');
        }
        // clear any active side button selection
        const sideButtons = document.getElementsByClassName('sideButton');
        for (const btn of sideButtons) {
            btn.classList.remove('active');
            btn.setAttribute('aria-selected', 'false');
        }
    }
}