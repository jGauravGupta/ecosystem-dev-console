function openPane(evt, tabName) {
    const tabcontent = document.getElementsByClassName('tabcontent');
    for (const tab of tabcontent) {
        tab.style.display = 'none';
        tab.setAttribute('aria-hidden', 'true');
    }

    const sideButtons = document.getElementsByClassName('sideButton');
    for (const btn of sideButtons) {
        btn.classList.remove('active');
        btn.setAttribute('aria-selected', 'false');
    }

    const activeTab = document.getElementById(tabName);
    if (activeTab) {
        activeTab.style.display = 'block';
        activeTab.setAttribute('aria-hidden', 'false');
    }

    evt.currentTarget.classList.add('active');
    evt.currentTarget.setAttribute('aria-selected', 'true');
    evt.currentTarget.focus();
    localStorage.setItem('activeTab', tabName);

    filterBeans();
}

document.getElementById('sidePane').addEventListener('keydown', e => {
    const btns = Array.from(document.querySelectorAll('.sideButton'));
    let idx = btns.findIndex(b => b.getAttribute('aria-selected') === 'true');
    if (e.key === 'ArrowDown') {
        idx = (idx + 1) % btns.length;
        btns[idx].focus();
        btns[idx].click();
        e.preventDefault();
    } else if (e.key === 'ArrowUp') {
        idx = (idx - 1 + btns.length) % btns.length;
        btns[idx].focus();
        btns[idx].click();
        e.preventDefault();
    }
});
