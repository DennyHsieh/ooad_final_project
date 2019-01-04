package team.t4.booking.tk;
/**
	 * This enum grasps all the concession plans with values which are
	 * names of tables in the database into a group.
	 */
	public enum ConcessionPlan {
		大學85("College85Price"),
		大學7("College7Price"),
		大學5("College5Price"),
		優惠("ConcessionPrice"),
		早鳥9("Early9Price"),
		早鳥8("Early8Price"),
		早鳥65("Early65Price"),
		無優惠("NormalPrice");
		
		public String getValue() {
			return new String(this.value);
		}
		private ConcessionPlan(String value) {
			this.value = value;
		}		
		private String value;
	}