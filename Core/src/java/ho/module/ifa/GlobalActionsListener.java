package ho.module.ifa;

import ho.core.util.HOLogger;
import ho.module.ifa.gif.Gif89Encoder;
import ho.module.ifa.gif.Quantize;
import ho.module.ifa.imagebuilder.ConfigManager;
import ho.module.ifa.imagebuilder.ImageDesignPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;


public class GlobalActionsListener extends MouseAdapter implements
		ActionListener {
	public static final int WIDTH = (int) GraphicsEnvironment
			.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
	public static final int HEIGHT = (int) GraphicsEnvironment
			.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
	private PluginIfaPanel pluginIfaPanel;
	private JDialog parent = null;

	public GlobalActionsListener(PluginIfaPanel pluginIfaPanel) {
		this.pluginIfaPanel = pluginIfaPanel;
	}

	public static void saveImage(boolean animated, boolean home, ImageDesignPanel imagePanel, Component parent) {
			try {
				ConfigManager.saveConfig(imagePanel);

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new ImageFileFilter(
						new String[] { "gif" }));
				fileChooser.setAcceptAllFileFilterUsed(false);
				if (animated)
					fileChooser.setSelectedFile(new File("animated.gif"));
				else if (!home)
					fileChooser.setSelectedFile(new File("visited.gif"));
				else
					fileChooser.setSelectedFile(new File("hosted.gif"));
				if (fileChooser.showSaveDialog(parent) != 0)
					return;
				OutputStream out = new FileOutputStream(fileChooser
						.getSelectedFile().getPath());
				
	

				if (animated) {
					JDialog dialog = new JDialog();
					dialog.getContentPane().setBackground(Color.white);
					dialog.setUndecorated(true);
					dialog.getContentPane().setLayout(null);

					
					JComponent panel1 = imagePanel.getHostedEmblemPanel()
							.getImage();
					JComponent panel2 = imagePanel.getVisitedEmblemPanel()
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
							imagePanel.getDelaySpinner().getValue()
									.toString()).doubleValue()));
					encoder.encode(out);
					out.close();

					dialog.dispose();
					imagePanel.refreshFlagPanel();
					return;
				}

				JComponent panel;
				
				if (home) {
					panel = imagePanel.getHostedEmblemPanel().getImage();
				} else {
					panel = imagePanel.getVisitedEmblemPanel().getImage();
				}
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
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
//		try {
//			JFileChooser fileChooser = new JFileChooser();
//			fileChooser.setFileFilter(new ImageFileFilter(new String[] { "jpg",
//					"gif" }));
//			fileChooser.setAcceptAllFileFilterUsed(false);
//			fileChooser.setMultiSelectionEnabled(false);
//			if (fileChooser.showOpenDialog(this.parent) == 0) {
//				ImageDesignPanel imageDesignPanel = this.pluginIfaPanel
//						.getImageDesignPanel();
//				EmblemPanel emblemPanel = imageDesignPanel.getEmblemPanel(0);
//				String path = fileChooser.getSelectedFile().getPath();
//				emblemPanel.setImagePath(path);
//				ImageIcon image = createImageIcon(path);
//				emblemPanel.setLogo(image);
//				imageDesignPanel.validate();
//				imageDesignPanel.repaint();
//			} else {
//				ImageDesignPanel imageDesignPanel = this.pluginIfaPanel
//						.getImageDesignPanel();
//				EmblemPanel emblemPanel = imageDesignPanel.getEmblemPanel(1);
//				emblemPanel.setLogo(null);
//				imageDesignPanel.validate();
//				imageDesignPanel.repaint();
//			}
//		} catch (Exception e) {
//			HOLogger.instance().error(GlobalActionsListener.class, e);
//		}
	}

	private static BufferedImage quantizeBufferedImage(BufferedImage bufferedImage)
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

	private ImageIcon createImageIcon(String path) {
		if (path != null) {
			return new ImageIcon(path);
		}
		HOLogger.instance().debug(this.getClass(), "Couldn't find file: " + path);
		return null;
	}

	private static int[][] getPixels(Image image) throws IOException {
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

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// TODO Auto-generated method stub
		
	}
}
