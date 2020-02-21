/*
 * DrawingModeHandler.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.controller;

import com.thunder_cut.graphics.feature.*;
import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;
import com.thunder_cut.graphics.ui.keys.HotKey;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.EnumMap;

import static java.util.Objects.nonNull;

public class DrawingModeHandler {
    private DrawingMode selectedDrawingMode;
    private EnumMap<DrawingMode, DrawingFeature> drawingFeatures;
    private Color color;

    public DrawingModeHandler() {
        selectedDrawingMode = DrawingMode.BRUSH;
        color = new Color(Color.BLACK.getRGB());

        drawingFeatures = new EnumMap<>(DrawingMode.class);
        drawingFeatures.put(DrawingMode.BRUSH, new Brush());
        drawingFeatures.put(DrawingMode.ERASER, new Eraser());
        drawingFeatures.put(DrawingMode.AREA_SELECTOR, new AreaSelector());
        drawingFeatures.put(DrawingMode.LINE, new LineDrawer());
        drawingFeatures.put(DrawingMode.RECTANGLE, new RectangleDrawer());
        drawingFeatures.put(DrawingMode.TRIANGLE, new TriangleDrawer());
        drawingFeatures.put(DrawingMode.CIRCLE, new CircleDrawer());

        HotKey.BRUSH.setAction(()->{
            drawingModeChanged(DrawingMode.BRUSH);
        });

        HotKey.ERASER.setAction(()->{
            drawingModeChanged(DrawingMode.ERASER);
        });

        HotKey.AREA_SELECT.setAction(()->{
            drawingModeChanged(DrawingMode.AREA_SELECTOR);
        });

        HotKey.COLOR_SELECT.setAction(()->{
            drawingModeChanged(DrawingMode.COLOR_CHOOSER);
        });

        HotKey.BRUSH_SIZE_UP.setAction(()->{
            Brush brush = ((Brush) drawingFeatures.get(DrawingMode.BRUSH));
            brush.setSize(brush.getSize()+2);
            Eraser eraser = ((Eraser) drawingFeatures.get(DrawingMode.ERASER));
            eraser.setSize(eraser.getSize()+2);
            LineDrawer lineDrawer = (LineDrawer) drawingFeatures.get(DrawingMode.LINE);
            lineDrawer.setSize(lineDrawer.getSize() + 2);
            RectangleDrawer rectDrawer = (RectangleDrawer) drawingFeatures.get(DrawingMode.RECTANGLE);
            rectDrawer.setSize(rectDrawer.getSize() + 2);
            TriangleDrawer triangleDrawer = (TriangleDrawer) drawingFeatures.get(DrawingMode.TRIANGLE);
            triangleDrawer.setSize(triangleDrawer.getSize() + 2);
            CircleDrawer circleDrawer = (CircleDrawer) drawingFeatures.get(DrawingMode.CIRCLE);
            circleDrawer.setSize(circleDrawer.getSize() + 2);
        });

        HotKey.BRUSH_SIZE_DOWN.setAction(()->{

            Brush brush = ((Brush) drawingFeatures.get(DrawingMode.BRUSH));
            if(brush.getSize()<=2){
                return;
            }
            brush.setSize(brush.getSize()-2);
            Eraser eraser = ((Eraser) drawingFeatures.get(DrawingMode.ERASER));
            eraser.setSize(eraser.getSize()-2);
            LineDrawer lineDrawer = (LineDrawer) drawingFeatures.get(DrawingMode.LINE);
            lineDrawer.setSize(lineDrawer.getSize() - 2);
            RectangleDrawer rectDrawer =(RectangleDrawer) drawingFeatures.get(DrawingMode.RECTANGLE);
            rectDrawer.setSize(rectDrawer.getSize() - 2);
            TriangleDrawer triangleDrawer = (TriangleDrawer) drawingFeatures.get(DrawingMode.TRIANGLE);
            triangleDrawer.setSize(triangleDrawer.getSize() - 2);
            CircleDrawer circleDrawer = (CircleDrawer) drawingFeatures.get(DrawingMode.CIRCLE);
            circleDrawer.setSize(circleDrawer.getSize() - 2);
        });

    }

    public void drawingModeChanged(DrawingMode mode) {
        if (mode == DrawingMode.COLOR_CHOOSER) {
            Color selectedColor = JColorChooser.showDialog(null, "Color", Color.GRAY);
            color = nonNull(selectedColor) ? selectedColor : color;
        } else if (mode == DrawingMode.SIZE_CHOOSER) {
            int size = Integer.parseInt(JOptionPane.showInputDialog("size"));
            ((Brush) drawingFeatures.get(DrawingMode.BRUSH)).setSize(size);
            ((Eraser) drawingFeatures.get(DrawingMode.ERASER)).setSize(size);
            ((LineDrawer) drawingFeatures.get(DrawingMode.LINE)).setSize(size);
            ((RectangleDrawer) drawingFeatures.get(DrawingMode.RECTANGLE)).setSize(size);
            ((TriangleDrawer) drawingFeatures.get(DrawingMode.TRIANGLE)).setSize(size);
            ((CircleDrawer) drawingFeatures.get(DrawingMode.CIRCLE)).setSize(size);
        } else {
            selectedDrawingMode = mode;
        }
    }

    public void handleMouseEvent(MouseData mouseData, CanvasPixelInfo canvasPixelInfo) {
        drawingFeatures.get(selectedDrawingMode).process(mouseData, canvasPixelInfo, color);
    }
}
