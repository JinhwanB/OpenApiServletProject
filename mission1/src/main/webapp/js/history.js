// 삭제 버튼 클릭 함수
function deleteHistory(id) {
    if (confirm("정말 삭제하시겠습니까?")) {
        location.href = `?id=${id}`;
        location.reload();
    }
}