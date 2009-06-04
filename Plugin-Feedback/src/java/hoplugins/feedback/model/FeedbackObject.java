package hoplugins.feedback.model;

import hoplugins.Feedback;
import hoplugins.feedback.constants.FeedbackConstants;

import java.util.List;
import java.util.Vector;

import plugins.IHOMiniModel;

public abstract class FeedbackObject {
	private int feedbackType;
	private long feedbackId;

	public FeedbackObject(int feedbackType, long feedbackId) {
		this.feedbackType = feedbackType;
		this.feedbackId = feedbackId;
	}

	/**
	 * Every SubClass of FeedbackObject needs to implements this static method
	 * TODO: throw NotImplementedException?
	 *
	 * @return the new build list
	 */
	public static List rebuildList() {
		List list = new Vector();
		return list;
	}
	/**
	 * @return Feedback Type (rating, training...)
	 * 			from FeedbackConstants
	 */
	public int getFeedbackType () {
		return feedbackType;
	}

	/**
	 * @return id for the feedback object (must be unique in this feedback type)
	 */
	public long getFeedbackId () {
		return feedbackId;
	}

	/**
	 * Creates a full URL with all informations for the upload
	 * @return url to open
	 */
	public abstract String createUrl ();

	/**
	 * Gets the miniModel from the Feedback class
	 */
	protected static IHOMiniModel getMiniModel () {
		return Feedback.getMiniModel();
	}
	
	/**
	 * Gets the Feedback Version  
	 */
	public static double getFeedbackVersion () {
		return Feedback.PLUGIN_VERSION;
	}
}
