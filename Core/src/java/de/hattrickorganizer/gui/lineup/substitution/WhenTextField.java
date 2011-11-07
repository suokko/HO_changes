package de.hattrickorganizer.gui.lineup.substitution;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import de.hattrickorganizer.tools.StringUtilities;

public class WhenTextField extends JFormattedTextField {

	private static final long serialVersionUID = 1207880109251770680L;

	public WhenTextField() {
		init();
	}

	private void init() {
		JFormattedTextField.AbstractFormatter editFormatter = new EditFormatter();
		JFormattedTextField.AbstractFormatter displayFormatter = new DisplayFormatter();
		DefaultFormatterFactory factory = new DefaultFormatterFactory(displayFormatter, displayFormatter,
				editFormatter);
		setFormatterFactory(factory);
		setValue(Integer.valueOf(0));

		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						selectAll();
					}
				});
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						commitEdit();
						transferFocus();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

	}

	private class DisplayFormatter extends JFormattedTextField.AbstractFormatter {

		private static final long serialVersionUID = -3082798484771841528L;

		@Override
		public Object stringToValue(String text) throws ParseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String valueToString(Object obj) throws ParseException {
			Integer value = (Integer) obj;
			if (value == null || value.intValue() == 0) {
				return "anytime";
			}
			return MessageFormat.format("after {0,number,integer} minutes", value);
		}

	}

	private class EditFormatter extends JFormattedTextField.AbstractFormatter {

		private static final long serialVersionUID = 4814824765566252119L;
		private DocumentFilter filter = new Filter();

		@Override
		public Object stringToValue(String text) throws ParseException {
			return (StringUtilities.isEmpty(text)) ? Integer.valueOf(0) : Integer.parseInt(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			return (value == null) ? "0" : value.toString();
		}

		@Override
		protected DocumentFilter getDocumentFilter() {
			return this.filter;
		}

		private class Filter extends DocumentFilter {

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				StringBuilder builder = new StringBuilder();
				Document doc = fb.getDocument();
				builder.append(doc.getText(0, doc.getLength()));
				builder.replace(offset, offset + length, text);
				String content = builder.toString();
				if (StringUtilities.isNumeric(content)) {
					int i = Integer.parseInt(content);
					if (i >= 0 && i <= 119) {
						super.replace(fb, offset, length, text, attrs);
					}
				}
			}
		}
	}
}
