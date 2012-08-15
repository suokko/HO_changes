package ho.module.ifa2;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.file.xml.XMLWorldDetailsParser;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.net.MyConnector;
import ho.core.util.HOLogger;
import hoplugins.pluginIFA.config.ConfigManager;
import hoplugins.pluginIFA.gif.Gif89Encoder;
import hoplugins.pluginIFA.gif.Quantize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class GlobalActionsListener implements ActionListener {
	public static final int WIDTH = (int) GraphicsEnvironment
			.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
	public static final int HEIGHT = (int) GraphicsEnvironment
			.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
	private TablesPanel pluginIfaPanel;
	private JDialog parent = null;

	public GlobalActionsListener(TablesPanel pluginIfaPanel) {
		this.pluginIfaPanel = pluginIfaPanel;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equalsIgnoreCase("saveImage")) {
			try {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new ImageFileFilter(
						new String[] { "gif" }));
				fileChooser.setAcceptAllFileFilterUsed(false);
				if (this.pluginIfaPanel.getImageDesignPanel().isAnimGif())
					fileChooser.setSelectedFile(new File("animated.gif"));
				else if (this.pluginIfaPanel.getImageDesignPanel().isHomeAway())
					fileChooser.setSelectedFile(new File("visited.gif"));
				else
					fileChooser.setSelectedFile(new File("hosted.gif"));
				if (fileChooser.showSaveDialog(this.parent) != 0)
					return;
				OutputStream out = new FileOutputStream(fileChooser
						.getSelectedFile().getPath());
				ImageDesignPanel imageDesignPanel = this.pluginIfaPanel
						.getImageDesignPanel();
				boolean isSelected = imageDesignPanel.getHome().isSelected();

				ConfigManager.saveConfig(imageDesignPanel);

				if (imageDesignPanel.isAnimGif()) {
					JDialog dialog = new JDialog();
					dialog.getContentPane().setBackground(Color.white);
					dialog.setUndecorated(true);
					dialog.getContentPane().setLayout(null);

					if (!isSelected) {
						imageDesignPanel.getHome().getActionListeners()[0]
								.actionPerformed(new ActionEvent(
										imageDesignPanel.getHome(), 0,
										"visited"));
					}
					JComponent panel1 = imageDesignPanel.getEmblemPanel(true)
							.getImage();
					imageDesignPanel.getAway().getActionListeners()[0]
							.actionPerformed(new ActionEvent(imageDesignPanel
									.getHome(), 0, "hosted"));
					JComponent panel2 = imageDesignPanel.getEmblemPanel(false)
							.getImage();

					Dimension size1 = panel1.getSize();
					Dimension size2 = panel2.getSize();
					int maxW = size1.width > size2.width ? size1.width
							: size2.width;
					int maxH = size1.height > size2.height ? size1.height
							: size2.height;
					panel1.setBounds(0, 0, size1.width, size1.height);
					panel2.setBounds(maxW, 0, size2.width, size2.height);

					dialog.getContentPane().add(panel1);
					dialog.getContentPane().add(panel2);
					dialog.setBounds(WIDTH + 1, HEIGHT + 1, 2 * maxW, maxH);
					dialog.setVisible(true);

					BufferedImage bufferedImage = new BufferedImage(
							dialog.getWidth(), dialog.getHeight(), 1);
					dialog.getContentPane().paintAll(
							bufferedImage.createGraphics());

					Gif89Encoder encoder = new Gif89Encoder();
					BufferedImage bufIma = quantizeBufferedImage(bufferedImage);
					encoder.addFrame(bufIma.getSubimage(0, 0, maxW, maxH));
					encoder.addFrame(bufIma.getSubimage(maxW, 0, maxW, maxH));
					encoder.setLoopCount(0);
					encoder.setUniformDelay((int) (100.0D * new Double(
							imageDesignPanel.getDelaySpinner().getValue()
									.toString()).doubleValue()));
					encoder.encode(out);
					out.close();

					if (isSelected)
						imageDesignPanel.getHome().getActionListeners()[0]
								.actionPerformed(new ActionEvent(
										imageDesignPanel.getHome(), 0,
										"visited"));
					else
						imageDesignPanel.getAway().getActionListeners()[0]
								.actionPerformed(new ActionEvent(
										imageDesignPanel.getAway(), 0, "hosted"));
					dialog.dispose();
					return;
				}

				JComponent panel = imageDesignPanel.getEmblemPanel(
						imageDesignPanel.isHomeAway()).getImage();
				BufferedImage bufferedImage = new BufferedImage(
						panel.getWidth(), panel.getHeight(), 1);
				panel.paintAll(bufferedImage.createGraphics());
				Gif89Encoder encoder = new Gif89Encoder();
				encoder.addFrame(quantizeBufferedImage(bufferedImage));
				encoder.encode(out);
				out.close();
			} catch (Exception e) {
				HOLogger.instance().error(GlobalActionsListener.class, e);
			}
		} else if (arg0.getActionCommand().equalsIgnoreCase("update")) {
			String worldDetails;
			try {
				worldDetails = MyConnector.instance().getWorldDetails(0);
				List<WorldDetailLeague> leagues = XMLWorldDetailsParser.parseDetails(XMLManager.parseString(worldDetails));
				DBManager.instance().saveWorldDetailLeagues(leagues);
				WorldDetailsManager.instance().refresh();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (HOVerwaltung.instance().getModel().getBasics().getTeamId() == 0)
				PluginIfaUtils.updateTeamTable();
			try {
				PluginIfaUtils.updateMatchesTable();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.pluginIfaPanel.getImageDesignPanel().refreshFlagPanel();
			this.pluginIfaPanel.getStatisticScrollPanelHome().refresh();
			this.pluginIfaPanel.getStatisticScrollPanelAway().refresh();
		}
	}

	private BufferedImage quantizeBufferedImage(BufferedImage bufferedImage)
			throws IOException {
		int[][] pixels = getPixels(bufferedImage);
		int[] palette = Quantize.quantizeImage(pixels, 256);
		int w = pixels.length;
		int h = pixels[0].length;
		int[] pix = new int[w * h];

		BufferedImage bufIma = new BufferedImage(w, h, 1);

		for (int x = w; x-- > 0;) {
			for (int y = h; y-- > 0;) {
				pix[(y * w + x)] = palette[pixels[x][y]];
				bufIma.setRGB(x, y, palette[pixels[x][y]]);
			}
		}
		return bufIma;
	}

	private int[][] getPixels(Image image) throws IOException {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		int[] pix = new int[w * h];
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, w, h, pix, 0, w);
		try {
			if (!grabber.grabPixels())
				throw new IOException("Grabber returned false: "
						+ grabber.status());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int[][] pixels = new int[w][h];
		for (int x = w; x-- > 0;) {
			for (int y = h; y-- > 0;) {
				pixels[x][y] = pix[(y * w + x)];
			}
		}

		return pixels;
	}

	private class ImageFileFilter extends FileFilter {
		String[] ext = new String[0];

		public ImageFileFilter(String[] ext) {
			this.ext = ext;
		}

		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			String extension = "";
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if ((i > 0) && (i < s.length() - 1)) {
				extension = s.substring(i + 1).toLowerCase();
			}
			if (extension != null) {
				for (int j = 0; j < this.ext.length; j++) {
					if (extension.equals(this.ext[j])) {
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public String getDescription() {
			return "Images";
		}
	}
}
