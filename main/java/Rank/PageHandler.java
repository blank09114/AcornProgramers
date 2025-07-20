package Rank;

public class PageHandler {
	int pageSize; //한 페이지의 글의 수
	int grpSize; //그룹의 사이즈
	int totRecords; //전체 레코드 수
	int currentPage; //현재 페이지
	int currentGrp; //현재 그룹
	int grpEndPage; //그룹의 끝
	int grpStartPage; //그룹의 시작
	int totalPage; //전체 페이지 수
	
	//생성자
	public PageHandler(int pageSize, int grpSize, int totRecords, int currentPage) {
		this.pageSize = pageSize;
		this.grpSize = grpSize;
		this.totRecords = totRecords;
		this.currentPage = currentPage;
		calc();
	}
	
	//페이지 계산
	private void calc() {
		// 전체 페이지 수 계산
		int remain = totRecords % pageSize; //나머지가 있는 경우
		
		if(remain == 0) {
			totalPage = totRecords / pageSize;
		}else {
			totalPage = totRecords / pageSize + 1;
		}
		
		//현재 페이지가 속한 그룹 번호 계산
		int remain2 = currentPage % grpSize;
		
		if(remain2 == 0) {
			currentGrp = currentPage / grpSize;
		}else {
			currentGrp = currentPage / grpSize + 1;
		}
		
		grpStartPage = (currentGrp - 1) * grpSize + 1; //현재 그룹의 시작 페이지 번호 계산
		grpEndPage = currentGrp * grpSize; //현재 그룹의 끝 페이지 번호 계산
		
		//그룹 끝 페이지가 전체 페이지 수를 초과하지 않도록 조정
		if(grpEndPage > totalPage) {
			grpEndPage = totalPage;
		}
	}

	//getter 함수
	public int getPageSize() {
		return pageSize;
	}
	public int getGrpSize() {
		return grpSize;
	}
	public int getTotRecords() {
		return totRecords;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public int getCurrentGrp() {
		return currentGrp;
	}
	public int getGrpEndPage() {
		return grpEndPage;
	}
	public int getGrpStartPage() {
		return grpStartPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	
	public static void main(String[] args) {
		PageHandler p = new PageHandler(5, 4, 33, 6);
		
		int currentGrp = p.getCurrentGrp();
		int getStart = p.getGrpStartPage();
		int grpEnd = p.getGrpEndPage();
		
		System.out.println(currentGrp);
		System.out.println(getStart);
		System.out.println(grpEnd);
	}
}
