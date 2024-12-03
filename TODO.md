

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
- [ ] /refrigerators/{냉장고id}/inventories/{재고id}
  - 냉장고 재고 수정, 소비 페이지
- [ ] /items/new
  - 아이템 추가
- [ ] recipes?category={카테고리id}
  - 레시피 조회
- [ ] recipes/new
  - 레시피 추가
- [ ] recipes/{레시피id}
  - 레시피 상세 조회
- [ ] recipes/{레시피id}/ingredients/items?category={카테고리id}
  - 레시피 재료 추가 (아이템 선택)
- [ ] recipes/{레시피id}/ingredients/items/{아이템id}
  - 레시피 재료 추가 (단위, 용량)
- [ ] recipes/{레시피id}/refrigerators
  - 레시피를 제작할 수 있는 냉장고 조회
- [ ] refrigerators/{냉장고id}/recipes
  - 냉장고에 있는 재료로 만들 수 있는 레시피 조회
- [ ] refrigerators/{냉장고id}/history
  - 냉장고 사용 내역 조회

추가
냉장고 공유
- [ ] /refrigerators/{냉장고id}/share
  - 냉장고 공유 관리
- [ ] /refrigerators/{냉장고id}/share/new
  - 냉장고 공유 추가
- [ ] /refrigerators/{냉장고id}/share/{공유id}
  - 냉장고 공유 수정, 삭제