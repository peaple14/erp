// 각 탭의 로드된 페이지를 저장할 객체
var loadedPages = {};
var dataPackget = {

}

function showTab(tabId, pageUrl) {
    // 모든 탭 내용을 숨김
    var tabContents = document.querySelectorAll('.tab-content');
    for (var i = 0; i < tabContents.length; i++) {
        tabContents[i].style.display = 'none';
    }

    // 선택한 탭의 내용을 보임
    var selectedTab = document.getElementById(tabId);
    if (selectedTab) {
        selectedTab.style.display = 'block';
        selectedTabId = pageUrl;

        // 페이지가 이미 로드되었는지 확인하고, 로드되지 않았다면 로드
        if (!loadedPages[tabId]) {
            loadTab(pageUrl, tabId);
        }
    }
}

function loadTab(pageUrl, tabId) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var tabContents = document.getElementById('tabContents');
            tabContents.innerHTML = this.responseText;

            // 추가: 탭 내부의 스크립트를 실행
            var scripts = tabContents.querySelectorAll('script');
            Array.from(scripts).forEach(function(script) {
                eval(script.innerHTML);
            });

            // 페이지가 로드되었음을 표시
            loadedPages[tabId] = true;
        }
    };
    xhttp.open("GET", pageUrl, true);
    xhttp.send()
    /* 나중에 참고
    if(pageUrl === undefined) {
        return;
    }

    fetch(pageUrl, { method: 'get' })
        .then(function(response){
            const data = await resposne.json();
            response.text().then(function(html){
                let html_dom = new DOMParser().parseFromString(html, 'text/html');
                console.log(html_dom.body);

                var tabContents = document.getElementById("tabContents");

                tabContents.appendChild(html_dom.body);
            })
        });*/
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
    newTab.addEventListener('click', function() {
        var activeTab = document.querySelector('.tab.active');
        if (activeTab) {
            activeTab.classList.remove('active');
        }

        if(selectedTabId == '/product_add') {
            // save data code;;;
            // jquery
            var _data = {
                product_name : $('input#productname').val(),




            }

            dataPackget[selectedTabId] = _data;
            // localStorage, SessionStorage
        }

        newTab.classList.add('active');
        showTab(newTabId, pageUrl);
        loadTab(pageUrl); // 해당 부분을 추가
    });

    // 닫기 버튼 추가
    var closeButton = document.createElement('span');
    closeButton.className = 'close-button';
    closeButton.innerHTML = '×';
    closeButton.addEventListener('click', function(event) {
        event.stopPropagation(); // 부모 요소의 이벤트 전파 방지
        removeTab(newTabId);
    });
    newTab.appendChild(closeButton);

    // 처음 탭 추가 시, 활성화
    if (tabsContainer.childElementCount === 1) {
        newTab.classList.add('active');
        showTab(newTabId, pageUrl);

        // 페이지 내용을 비동기적으로 로드
        loadTab(pageUrl, newTabId);
    }
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
            <li><a href="#" onclick="addTab('발주 거래처', '/company_list')">발주 거래처</a></li>
            <li><a href="#" onclick="addTab('수주 거래처', '/company_add')">수주 거래처</a></li>
            <li><a href="#" onclick="addTab('미수금', '/company_list')">미수금</a></li>
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
                        <li><a href="#" onclick="addTab('주문현황','/order_list')">주문현황</a></li>
                        <li><a href="#" onclick="addTab('발주', '/notice_add')">발주</a></li>
                        <li><a href="#" onclick="addTab('수주')">수주</a></li>
                    </ul>
                `;
    } else if (menu === 'menu5') {
        sidebarContent = `
                    <ul class="sidebar-links">
                        <li><a href="#" onclick="addTab('고객불만접수','/problem_list')">고객불만접수</a></li>
                        <li><a href="#" onclick="addTab('공지사항','/notice_list')">공지사항</a></li>
                    </ul>
                `;
    }
    sidebar.innerHTML = sidebarContent;
}