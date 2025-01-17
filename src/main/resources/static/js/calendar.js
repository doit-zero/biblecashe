document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');
    const daysOfWeekEl = document.getElementById('daysOfWeek');
    const monthSelect = document.getElementById('monthSelect');
    const todayButton = document.getElementById('todayButton');
    const yearText = document.getElementById('yearText');
    const verseButton = document.getElementById('verseButton');
    const today = new Date();

    let currentYear = 2025; // 고정된 연도 2025
    let currentMonth = today.getMonth();
    let selectedDate = today; // 기본적으로 오늘 날짜 선택

    const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

    // 초기화
    populateMonthSelect();
    updateSelectValues();
    renderCalendar(currentYear, currentMonth);

    // 이벤트 리스너
    monthSelect.addEventListener('change', function () {
        currentMonth = parseInt(monthSelect.value, 10);
        renderCalendar(currentYear, currentMonth);
    });

    todayButton.addEventListener('click', function () {
        currentMonth = today.getMonth();
        selectedDate = today; // 오늘 날짜로 설정
        renderCalendar(currentYear, currentMonth);
        updateVerseButton('오늘의 말씀');
    });

    // "오늘의 말씀" 버튼 클릭 시 선택된 날짜 전송
    verseButton.addEventListener('click', function () {
        if (selectedDate) {
            const year = selectedDate.getFullYear();
            const month = selectedDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
            const date = selectedDate.getDate();

            // 서버에 선택된 날짜 전송 (서버로 이동)
            const url = `/contents?year=${year}&month=${month}&date=${date}`;

            // 페이지 이동 (서버로 요청을 보내는 방식)
            window.location.href = url;
        } else {
            alert("먼저 날짜를 선택해주세요.");
        }
    });

    function populateMonthSelect() {
        for (let month = 0; month < 12; month++) {
            const option = document.createElement('option');
            option.value = month;
            option.textContent = `${month + 1}월`;
            monthSelect.appendChild(option);
        }
    }

    function updateSelectValues() {
        monthSelect.value = currentMonth;
    }

    function renderCalendar(year, month) {
        calendarEl.innerHTML = ''; // 기존 달력 초기화
        daysOfWeekEl.innerHTML = ''; // 요일 초기화

        // 요일 렌더링
        daysOfWeek.forEach((day) => {
            const dayEl = document.createElement('div');
            dayEl.className = 'day-name';
            dayEl.textContent = day;
            daysOfWeekEl.appendChild(dayEl);
        });

        // 날짜 계산
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        const firstDay = new Date(year, month, 1).getDay();

        // 빈 셀 추가
        for (let i = 0; i < firstDay; i++) {
            const emptyDay = document.createElement('div');
            emptyDay.className = 'calendar-day empty';
            calendarEl.appendChild(emptyDay);
        }

        // 날짜 생성
        for (let day = 1; day <= daysInMonth; day++) {
            const dayEl = document.createElement('div');
            dayEl.className = 'calendar-day';

            const date = new Date(year, month, day);

            // 오늘 날짜는 빨간 점 표시
            if (
                date.getFullYear() === today.getFullYear() &&
                date.getMonth() === today.getMonth() &&
                date.getDate() === today.getDate()
            ) {
                dayEl.classList.add('today');
                const dot = document.createElement('div');
                dot.className = 'dot';
                dayEl.appendChild(dot);
            }

            // 선택한 날짜는 다른 스타일 적용
            if (
                selectedDate &&
                date.getFullYear() === selectedDate.getFullYear() &&
                date.getMonth() === selectedDate.getMonth() &&
                date.getDate() === selectedDate.getDate()
            ) {
                dayEl.classList.add('selected');
            }

            // 오늘 이후 날짜는 선택 불가
            if (date > today) {
                dayEl.classList.add('disabled');
                dayEl.style.pointerEvents = 'none';
            }

            // 날짜 클릭 시 오늘의 말씀으로 변경
            dayEl.textContent = day;
            dayEl.addEventListener('click', function () {
                if (date <= today) { // 오늘 이전 날짜는 클릭 시 말씀이 표시
                    selectedDate = date;
                    renderCalendar(currentYear, currentMonth);
                    if (date.getFullYear() === today.getFullYear() &&
                        date.getMonth() === today.getMonth() &&
                        date.getDate() === today.getDate()) {
                        updateVerseButton('오늘의 말씀');
                    } else {
                        updateVerseButton(`${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일의 말씀`);
                    }
                }
            });

            calendarEl.appendChild(dayEl);
        }
    }

    function updateVerseButton(text) {
        verseButton.textContent = text;
    }

    window.showVerse = function () {
        alert('오늘의 말씀: "나는 길이요 진리요 생명이다" (요한복음 14:6)');
    };
});