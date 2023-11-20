// 각 탭의 로드된 페이지를 저장할 객체
var loadedPages = {};
var selectedTabId = null; // 현재 선택된 탭의 ID
var dataPackage = {}; // 데이터 패키지
var tabCounter = 0; // 탭이 생성된 순서를 저장할 변수

function showTab(tabId, pageUrl, tabIdx) {
    // 모든 탭 내용을 숨김
    var tabContents = document.querySelectorAll('.tab-content');
    for (var i = 0; i < tabContents.length; i++) {
        tabContents[i].style.display = 'none';
    }

    // 선택한 탭의 내용을 보임
    var selectedTab = document.getElementById(tabId);
    if (selectedTab) {
        selectedTab.style.display = 'block';

        // 페이지가 이미 로드되었는지 확인하고, 로드되지 않았다면 로드
        if (!loadedPages[tabId] ) {
            loadTab(pageUrl, tabId, tabIdx);
        }
    }
}


function loadTab(pageUrl, tabId, tabIdx) {
    // var xhttp = new XMLHttpRequest();
    // xhttp.onreadystatechange = function() {
    //     if (this.readyState == 4 && this.status == 200) {
    //         var tabContents = document.getElementById('tabContents');
    //         tabContents.innerHTML = this.responseText;
    //
    //         // 추가: 탭 내부의 스크립트를 실행
    //         var scripts = tabContents.querySelectorAll('script');
    //         Array.from(scripts).forEach(function(script) {
    //             eval(script.innerHTML);
    //         });
    //
    //         // 페이지가 로드되었음을 표시
    //         loadedPages[tabId] = true;
    //     }
    // };
    // xhttp.open("GET", pageUrl, true);
    // xhttp.send()
    // 나중에 참고
    if (loadedPages[tabId]) {
        var tabContents = document.getElementById('tabContents');
        tabContents.innerHTML = '';
        tabContents.appendChild(loadedPages[tabId]);
        executeScripts(loadedPages[tabId]); // 추가: 스크립트 실행
    } else {
        // 새로운 페이지를 가져와서 로드
        fetch(pageUrl, { method: 'get' })
            .then(function (response) {
                response.text().then(function (html) {
                    let html_dom = new DOMParser().parseFromString(html, 'text/html');

                    var tabContents = document.getElementById('tabContents');
                    tabContents.innerHTML = '';
                    var temp = html_dom.body;
                    tabContents.appendChild(temp);
                    tabCounter++; // 탭이 생성될 때마다 카운터 증가
                    temp.id = 'tabContent' + tabCounter;

                    // 추가: 스크립트 실행
                    executeScripts(temp);

                    // 페이지가 로드되었음을 표시하고 저장
                    loadedPages[tabId] = html_dom.body;
                })
            });
    }
}

function executeScripts(container) {
    // 페이지 내의 스크립트 실행
    var scripts = container.querySelectorAll('script');
    scripts.forEach(function (script) {
        var newScript = document.createElement('script');
        newScript.text = script.text;
        document.head.appendChild(newScript).parentNode.removeChild(newScript);
    });
}
function addTab(tabName, pageUrl) {
    // 새로운 탭 생성
    var tabsContainer = document.getElementById('tabs');
    var newTabId = 'tab' + (tabsContainer.childElementCount + 1); // 고유한 ID 생성
    var newTab = document.createElement('li');
    newTab.className = 'tab';
    newTab.id = newTabId;
    newTab.innerHTML = tabName;
    tabsContainer.appendChild(newTab);

    // 탭 클릭 이벤트 핸들러 등록
    newTab.addEventListener('click', function () {
        var activeTab = document.querySelector('.tab.active');
        if (activeTab) {
            activeTab.classList.remove('active');
        }

        if (selectedTabId == '/product_add') {
            // save data code;;;
            // jquery
            var _data = {
                product_name: $('input#productname').val(),
                // Add other data properties as needed...
            }

            dataPackage[selectedTabId] = _data;
            // localStorage, SessionStorage
        }

        newTab.classList.add('active');
        showTab(newTabId, pageUrl,newTab.id);
    });

    // 닫기 버튼 추가
    var closeButton = document.createElement('span');
    closeButton.className = 'close-button';
    closeButton.innerHTML = '×';
    closeButton.addEventListener('click', function (event) {
        event.stopPropagation(); // 부모 요소의 이벤트 전파 방지
        removeTab(newTabId);
    });
    newTab.appendChild(closeButton);

    // 처음 탭 추가 시, 활성화
    if (tabsContainer.childElementCount === 1) {
        newTab.classList.add('active');
        // showTab(newTabId, pageUrl);
    }

    // 새로운 탭으로 이동
    newTab.click();
}

function removeTab(tabId) {
    // 탭 제거
    var tabsContainer = document.getElementById('tabs');
    var tabToRemove = document.getElementById(tabId);
    tabsContainer.removeChild(tabToRemove);

    // 탭 내용 제거
    var tabContents = document.getElementById('tabContents');
    var tabContentToRemove = document.getElementById(tabId);
    if (tabContentToRemove) {
        tabContents.removeChild(tabContentToRemove);
    }

    // 삭제된 탭 다음 탭을 활성화 (만약 삭제된 탭이 활성화 상태였다면)
    var activeTab = document.querySelector('.tab.active');
    if (!activeTab && tabsContainer.childElementCount > 0) {
        var firstTab = tabsContainer.children[0];
        firstTab.classList.add('active');
        showTab(firstTab.id);
    } else if (tabsContainer.childElementCount === 0) {
        // 모든 탭이 삭제되었을 때 화면 내용을 비우기
        tabContents.innerHTML = '';
    }
}

function changeSidebar(menu) {
    event.preventDefault(); // 기본 동작을 중단

    var sidebar = document.getElementById('sidebar');
    var sidebarContent = '';

    if (menu === 'menu1') {
        sidebarContent = `
        <ul class="sidebar-links">
            <li><a href="#" onclick="addTab('제품관리', '/product_list')">제품관리</a></li>
        </ul>
    `;
    } else if (menu === 'menu2') {
        sidebarContent = `
        <ul class="sidebar-links">
            <li><a href="#" onclick="addTab('거래처관리', '/company_list')">거래처관리</a></li>
            <li><a href="#" onclick="addTab('미수금', '/not_receive_list')">미수금</a></li>
        </ul>
    `;
    } else if (menu === 'menu3') {
        sidebarContent = `
                    <ul class="sidebar-links">
                        <li><a href="#" onclick="addTab('견적서관리','/quote_list')">견적서관리</a></li>
                        <li><a href="#" onclick="addTab('지출결의서','/expend_list')">지출결의서</a></li>
                    </ul>
                `;
    } else if (menu === 'menu4') {
        sidebarContent = `
                    <ul class="sidebar-links">
                        <li><a href="#" onclick="addTab('주문현황','/go_order_list')">주문현황</a></li>
                        <li><a href="#" onclick="addTab('주문완료', '/end_order_list')">주문완료</a></li>
                    </ul>
                `;
    } else if (menu === 'menu5') {
        sidebarContent = `
                    <ul class="sidebar-links">
                        <li><a href="#" onclick="addTab('공지사항','/notice_list')">공지사항</a></li>
                        <li><a href="#" onclick="addTab('고객불만접수','/problem_list')">고객불만접수</a></li>
                    </ul>
                `;
    }
    sidebar.innerHTML = sidebarContent;
}
