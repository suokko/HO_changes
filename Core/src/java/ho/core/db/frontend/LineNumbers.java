package ho.core.db.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

final class LineNumbers extends JComponent implements AdjustmentListener {

	private static final long serialVersionUID = -7856644869730409408L;
    private Dimension d;
    protected boolean showing;
    private int BAR;
    private JTextComponent src;
    private JScrollPane scroller;
    private Dimension sizeCache;
    private Point locCache;

	public LineNumbers(JTextPane src, JScrollPane scroller) {
        d = new Dimension();
        showing = true;
        BAR = 4;
        sizeCache = new Dimension();
        locCache = new Point();
        this.src = src;
        this.scroller = scroller;
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (showing)
                    hideBar();
                else
                    showBar();
            }
        });

        scroller.getVerticalScrollBar().addAdjustmentListener(this);
        super.setFont(src.getFont());
        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public void adjustmentValueChanged(AdjustmentEvent ae) {
        scroller.validate();
    }

    protected void hideBar() {
        showing = false;
        scroller.setRowHeaderView(this);
    }

    protected void showBar(){
        showing = true;
        scroller.setRowHeaderView(this);
    }

    private void paintNumbers(Graphics g) {
        g.setColor(UIManager.getColor("InternalFrame.activeTitleBackground"));
        Rectangle r = g.getClipBounds();
        Insets insets = getBorder().getBorderInsets(this);
        r.width -= insets.right + insets.left;
        r.x += insets.left;
        ((Graphics2D)g).fill(r);
        int ascent = getFontMetrics(getFont()).getAscent();
        int h = getFontMetrics(getFont()).getHeight();
        int y = (int)(r.getY() / (double)h) * h;
        int max = (int)(r.getY() + r.getHeight()) / h;
        g.setColor(Color.WHITE);
        for(int i = (int)Math.floor(y / h) + 1; i <= max + 1; i++)
        {
            g.drawString((new StringBuffer(String.valueOf(i))).toString(), insets.left, y + ascent);
            y += h;
        }

    }

    @Override
	public Dimension getPreferredSize() {
        d.width = getMyWidth();
        d.height = src.getHeight();
        return d;
    }

    private int getMyWidth() {
        FontMetrics fm = src.getFontMetrics(src.getFont());
        return showing ? fm.stringWidth((new StringBuffer(String.valueOf(getVisibleEndLine()))).toString()) + 4 + BAR : BAR;
    }

    private int getVisibleEndLine() {
        scroller.getViewport().getView().getLocation(locCache);
        scroller.getViewport().getSize(sizeCache);
        int h = getFontMetrics(getFont()).getHeight();
        return (int)Math.abs(-locCache.getY() + sizeCache.getHeight()) / h;
    }

    @Override
	public void paint(Graphics g){
        getBorder().paintBorder(this, g, 0, 0, d.width, d.height + 1);
        if(showing)
            paintNumbers(g);
    }

}