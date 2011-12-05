package hoplugins.feedback.dao;

import hoplugins.Commons;
import hoplugins.feedback.model.FeedbackObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class FeedbackStatusDAO {
	// ~ Static fields/initializers
	// -----------------------------------------------------------------

	/** Name of the table in the HO database */
	private static final String TABLE_NAME = "FEEDBACK_UPLOAD";

	static {
		checkTable();
	}

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Private default constuctor to prevent class instantiation.
	 */
	private FeedbackStatusDAO() {
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	public static void setUploaded(int feedbackType, long feedbackId) {
		final String query = "update " + TABLE_NAME + " set STATUS = TRUE "
				+ "where FEEDBACKTYPE=" + feedbackType + " AND FEEDBACKID="
				+ feedbackId;
		final int count = Commons.getModel().getAdapter().executeUpdate(query);

		if (count == 0) {
			Commons.getModel().getAdapter().executeUpdate(
					"insert into " + TABLE_NAME
							+ " (FEEDBACKTYPE, FEEDBACKID, STATUS) "
							+ "values (" + feedbackType + ", " + feedbackId
							+ ", TRUE)");
		}
	}

	public static boolean isUploaded(int feedbackType, long feedbackId) {
		final String query = "select STATUS from " + TABLE_NAME
				+ " where FEEDBACKTYPE=" + feedbackType + " AND FEEDBACKID="
				+ feedbackId;
		final ResultSet rs = Commons.getModel().getAdapter()
				.executeQuery(query);

		try {
			rs.next();
			return rs.getBoolean("STATUS");
		} catch (SQLException e) {
			return false;
		}
	}

	public static void reset() {
		final String query = "DELETE from " + TABLE_NAME;
		Commons.getModel().getAdapter().executeUpdate(query);
	}

	/**
	 * Check if the table exists, if not create it with default values
	 */
	public static void checkTable() {
		try {
			final ResultSet rs = Commons.getModel().getAdapter().executeQuery(
					"select * from " + TABLE_NAME);
			rs.next();
		} catch (Exception e) {
			Commons
					.getModel()
					.getAdapter()
					.executeUpdate(
							"CREATE TABLE "
									+ TABLE_NAME
									+ " (FEEDBACKTYPE INTEGER, FEEDBACKID BIGINT, STATUS BOOLEAN)");
			Commons.getModel().getAdapter().executeUpdate(
					"CREATE INDEX IDX_FEEDBACK ON " + TABLE_NAME
							+ " (FEEDBACKTYPE, FEEDBACKID)");
		}
	}

	public static void setUploaded(FeedbackObject fo) {
		setUploaded(fo.getFeedbackType(), fo.getFeedbackId());
	}

	public static boolean isUploaded(FeedbackObject fo) {
		return isUploaded(fo.getFeedbackType(), fo.getFeedbackId());
	}
}
