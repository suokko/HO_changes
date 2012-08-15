package ho.module.ifa2.config;

public enum Config {

	VISITED_FLAG_WIDTH("IFA_VisitedFlagWidth"), 
	HOSTED_FLAG_WIDTH("IFA_HostedFlagWidth"),
	SHOW_VISITED_HEADER("IFA_ShowVisitedHeader"),
	SHOW_HOSTED_HEADER("IFA_ShowHostedHeader"),
	SHOW_VISITED_FOOTER("IFA_ShowVisitedFooter"),
	SHOW_HOSTED_FOOTER("IFA_ShowHostedFooter"),
	VISITED_ROUNDLY("IFA_VisitedRoundly"),
	VISITED_GREY("IFA_VisitedGrey"),
	HOSTED_GREY("IFA_HostedGrey"),
	VISITED_BRIGHTNESS("IFA_VisitedBrightness"),
	HOSTED_BRIGHTNESS("IFA_HostedBrightness"),
	VISITED_HEADER_TEXT("IFA_VisitedHeaderText"),
	HOSTED_HEADER_TEXT("IFA_HostedHeaderText"),
	ANIMATED_GIF("IFA_AnimatedGif"),
	ANIMATED_GIF_DELAY("IFA_AnimatedGifDelay"),
	VISITED_EMBLEM_PATH("IFA_VisitedEmblemPath"),
	HOSTED_EMBLEM_PATH("IFA_HostedEmblemPath");

	private String txt;

	private Config(String text) {
		this.txt = text;
	}

	@Override
	public String toString() {
		return txt;
	}
}
