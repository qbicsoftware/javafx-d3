package com.github.javafxd3.demo.client.democases.svg.brush;

import com.github.javafxd3.api.D3;
import com.github.javafxd3.api.color.Colors;
import com.github.javafxd3.api.core.Selection;
import com.github.javafxd3.api.core.Value;
import com.github.javafxd3.api.event.D3Event;
import com.github.javafxd3.api.functions.DatumFunction;
import com.github.javafxd3.api.scales.LinearScale;
import com.github.javafxd3.api.svg.Axis;
import com.github.javafxd3.api.svg.Axis.Orientation;
import com.github.javafxd3.api.svg.Brush;
import com.github.javafxd3.api.svg.Brush.BrushEvent;
import com.github.javafxd3.api.wrapper.Element;
import com.github.javafxd3.api.wrapper.Node;
import com.github.javafxd3.demo.client.AbstractDemoCase;
import com.github.javafxd3.demo.client.DemoCase;
import com.github.javafxd3.demo.client.DemoFactory;
import com.github.javafxd3.demo.client.democases.Margin;

import javafx.scene.layout.VBox;

/**
 * FIXME find another Slider component
 *
 * 
 *
 */
public class BrushAsSliderDemo extends AbstractDemoCase {

	// #region ATTRIBUTES

	private Brush brush;
	private LinearScale x;
	private Selection handle;

	// #end region

	// #region CONSTRUCTORS

	/**
	 * @param d3
	 * @param demoPreferenceBox
	 */
	public BrushAsSliderDemo(D3 d3, VBox demoPreferenceBox) {
		super(d3, demoPreferenceBox);
		// this.css = Bundle.INSTANCE.css(); @Source("BrushAsSliderDemo.css")
		// axis, domain, slider, handle, halo
	}

	// #end region

	// #region METHODS

	/**
	 * Factory provider
	 * 
	 * @param d3
	 * @param demoPreferenceBox
	 * @return
	 */
	public static DemoFactory factory(D3 d3, VBox demoPreferenceBox) {
		return new DemoFactory() {
			@Override
			public DemoCase newInstance() {
				return new BrushAsSliderDemo(d3, demoPreferenceBox);
			}
		};
	}

	@Override
	public void start() {
		Margin margin = new Margin(200, 50, 200, 50);
		int width = 960 - margin.left - margin.right, height = 500 - margin.bottom - margin.top;

		x = d3.scale().linear().domain(0, 180).range(0, width).clamp(true);

		brush = d3.svg().brush().x(x).extent(0, 0).on(BrushEvent.BRUSH, new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int index) {
				brushed(context);
				return null;
			}
		});

		Selection svg = d3.select("root")
				// .style("width", width + margin.left + margin.right + "px")
				.append("svg").attr("width", width + margin.left + margin.right)
				.attr("height", height + margin.top + margin.bottom).append("g")
				.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

		
			Selection svg2 = svg.append("g").attr("class", "x " + "axis").attr("transform", "translate(0," + height / 2 + ")");
			
			Axis axis = d3.svg().axis().scale(x).orient(Orientation.BOTTOM).tickFormat(new DatumFunction<String>() {
				@Override
				public String apply(final Element context, final Value d, final int index) {
					return d.asString() + "A";
				}
			}).tickSize(0).tickPadding(12);
			
			DatumFunction<Element> datumFunction = new DatumFunction<Element>() {
				@Override
				public Element apply(final Element context, final Value d, final int index) {
					Node cloneNode = context.cloneNode(true);
					context.getParentNode().appendChild(cloneNode);
					return cloneNode.cast();
				}
			};
			
			svg2.call(axis).select("." + "domain").select(datumFunction).attr("class", "halo");
		

		Selection slider = svg.append("g").attr("class", "slider").call(brush);

		slider.selectAll(".extent,.resize").remove();

		slider.select(".background").attr("height", height);

		handle = slider.append("circle").attr("class", "handle")
				.attr("transform", "translate(0," + height / 2 + ")").attr("r", 9);

		slider.call(brush.event()).transition() // gratuitous intro!
				.duration(750).call(brush.extent(70, 70)).call(brush.event());

	}

	private void brushed(final Element context) {
		double value = brush.<Double> extent()[0];

		if (d3.<D3Event> event().sourceEvent() != null) { // not a programmatic
															// event
			value = x.invert(d3.mouse(context)[0].doubleValue()).asDouble();
			brush.extent(value, value);
		}

		Colors colors = new Colors(webEngine);

		handle.attr("cx", x.apply(value).asString());
		d3.select("root").style("background-color", colors.hsl((int) value, .8, .8).toHexaString());
	}

	@Override
	public void stop() {

	}

	// #end region

}