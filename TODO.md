

### TODO
- [x] /login
  - 로그인
- [x] /register
  - 로그아웃
- [x] /refrigerators
  - 냉장고 리스트 조회
- [x] /refrigerators/new
  - 냉장고 추가
- [x] /refrigerators/{냉장고id}/inventories
  - 냉장고 재고 조회
- [x] /refrigerators/{냉장고id}/inventories/items?category={카테고리id}
  - 냉장고 재고 추가 (아이템 선택)
- [x] /refrigerators/{냉장고id}/inventories/new?itemId={itemId}
  - 냉장고 재고 추가 (단위, 용량, 유통기한)
- [x] /refrigerators/{냉장고id}/inventories/{재고id}
  - 냉장고 재고 수정, 소비 페이지
- [x] /items/new
  - 아이템 추가
- [x] /recipes?category={카테고리id}
  - 레시피 조회
- [x] recipes/new
  - 레시피 추가
- [x] recipes/{레시피id}
  - 레시피 상세 조회
- [x] /recipes/{레시피id}/ingredients/items?category={카테고리id}
  - 레시피 재료 추가 (아이템 선택)
- [x] /recipes/{레시피id}/ingredients/items/new?itemId={아이템id}
  - 레시피 재료 추가 (단위, 용량)
- [x] recipes/{레시피id}/refrigerators
  - 레시피를 제작할 수 있는 냉장고 조회
- [x] refrigerators/{냉장고id}/recipes
  - 냉장고에 있는 재고로 만들 수 있는 레시피 조회
- [x] refrigerators/{냉장고id}/history
  - 냉장고 사용 내역 조회

추가
냉장고 공유
- [x] /refrigerators/{냉장고id}/share
  - 냉장고 공유 관리
- [x] /refrigerators/{냉장고id}/share/new
  - 냉장고 공유 추가
- [x] /refrigerators/{냉장고id}/share/{공유id}
  - 냉장고 공유 수정, 삭제
- [x] 재고 삭제
- [x] 레시피 삭제