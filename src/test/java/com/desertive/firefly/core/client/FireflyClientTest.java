package com.desertive.firefly.core.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.desertive.firefly.core.data.builders.SectionBuilder;
import com.greghaskins.spectrum.Spectrum;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static com.greghaskins.spectrum.dsl.specification.Specification.describe;
import static com.greghaskins.spectrum.dsl.specification.Specification.it;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Spectrum.class)
@SpringBootTest
@ActiveProfiles("test")
public class FireflyClientTest {

    /* * * * * * * * * * * * * * *
     * Rules and class variables *
     * * * * * * * * * * * * * * */

    @ClassRule
    public static final SpringClassRule classRule = new SpringClassRule();

    @Rule
    public SpringMethodRule methodRule = new SpringMethodRule();

    @Autowired
    FireflyClientImpl fireflyClient;

    /* * * * * * * * * *
     *      Tests      *
     * * * * * * * * * */

    {
        describe("Static type", () -> {

            it("should return one frame", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new SectionBuilder()
                        .setStart(0)
                        .setEnd(3)
                        .setType(ActionType.STATIC)
                        .addColor(100, 200, 150)
                        .build()
                    )
                ));

                assertEquals(frames.size(), 1);

            });

            it("should return different colors for different sections", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new SectionBuilder()
                        .setStart(0)
                        .setEnd(2)
                        .setType(ActionType.STATIC)
                        .addColor(242, 194, 8)
                        .addColor(1, 1, 1)
                        .build(),
                    new SectionBuilder()
                        .setStart(2)
                        .setEnd(5)
                        .setType(ActionType.STATIC)
                        .addColor(51, 46, 64)
                        .build(),
                    new SectionBuilder()
                        .setStart(6)
                        .setEnd(11)
                        .setType(ActionType.STATIC)
                        .addColor(1, 218, 53)
                        .build()
                    )
                ));

                assertTrue(getFrameColor(frames, 0, 0)
                    .equals(new Color(242, 194, 8)));
                assertTrue(getFrameColor(frames, 0, 1)
                    .equals(new Color(1, 1, 1)));
                assertTrue(getFrameColor(frames, 0, 2)
                    .equals(new Color(242, 194, 64)));
                assertTrue(getFrameColor(frames, 0, 3)
                    .equals(new Color(51, 46, 64)));
                assertTrue(getFrameColor(frames, 0, 5)
                    .equals(new Color(51, 46, 64)));
                assertTrue(getFrameColor(frames, 0, 6)
                    .equals(new Color(1, 218, 53)));
                assertTrue(getFrameColor(frames, 0, 11)
                    .equals(new Color(1, 218, 53)));

            });

            it("should skip every second index", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new SectionBuilder()
                        .setStart(0)
                        .setEnd(3)
                        .setEvery(2) // Use every second index
                        .setType(ActionType.STATIC)
                        .addColor(100, 100, 100)
                        .build()
                    )
                ));

                assertEquals(frames.size(), 1);
                assertTrue(getFrameColor(frames, 0, 0)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 0, 1) == null);
                assertTrue(getFrameColor(frames, 0, 2)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 0, 3) == null);

            });

            it("should skip indexes which are not defined in the subsections", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new SectionBuilder()
                        .setStart(0)
                        .setEnd(5)
                        .addSubsection(0, 2)
                        .addSubsection(5)
                        .setType(ActionType.STATIC)
                        .addColor(100, 100, 100)
                        .build()
                    )
                ));

                assertEquals(frames.size(), 1);
                assertTrue(getFrameColor(frames, 0, 0)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 0, 1)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 0, 2)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 0, 3) == null);
                assertTrue(getFrameColor(frames, 0, 4) == null);
                assertTrue(getFrameColor(frames, 0, 5)
                    .equals(new Color(100, 100, 100)));

            });

        });

        describe("Blink type", () -> {

            it("should return blink animation with one section", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new SectionBuilder()
                        .setStart(0)
                        .setEnd(0)
                        .setType(ActionType.BLINK)
                        .addColor(100, 50, 200)
                        .addProperty("transition", 4)
                        .build()
                    )
                ));

                assertEquals(frames.size(), 8);
                assertTrue(getFrameColor(frames, 0, 0)
                    .equals(new Color(0, 0, 0)));
                assertTrue(getFrameColor(frames, 1, 0)
                    .equals(new Color(25, 12, 50)));
                assertTrue(getFrameColor(frames, 2, 0)
                    .equals(new Color(50, 25, 100)));
                assertTrue(getFrameColor(frames, 3, 0)
                    .equals(new Color(75, 37, 150)));
                assertTrue(getFrameColor(frames, 4, 0)
                    .equals(new Color(100, 50, 200)));
                assertTrue(getFrameColor(frames, 5, 0)
                    .equals(new Color(75, 37, 150)));
                assertTrue(getFrameColor(frames, 6, 0)
                    .equals(new Color(50, 25, 100)));
                assertTrue(getFrameColor(frames, 7, 0)
                    .equals(new Color(25, 12, 50)));

            });
            
            it("should return blink animation with multiple colors", () -> {
                
                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                        new SectionBuilder()
                            .setStart(0)
                            .setEnd(0)
                            .setType(ActionType.BLINK)
                            .addColor(100, 100, 100)
                            .addColor(140, 140, 140)
                            .addColor(160, 120, 100)
                            .addColor(0, 0, 0)
                            .addProperty("transition", 2)
                            .build()
                        )
                    ));

                    assertEquals(frames.size(), 8);
                    assertTrue(getFrameColor(frames, 0, 0)
                        .equals(new Color(100, 100, 100)));
                    assertTrue(getFrameColor(frames, 1, 0)
                        .equals(new Color(120, 120, 120)));
                    assertTrue(getFrameColor(frames, 2, 0)
                        .equals(new Color(140, 140, 140)));
                    assertTrue(getFrameColor(frames, 3, 0)
                        .equals(new Color(150, 130, 120)));
                    assertTrue(getFrameColor(frames, 4, 0)
                        .equals(new Color(160, 120, 100)));
                    assertTrue(getFrameColor(frames, 5, 0)
                        .equals(new Color(80, 60, 50)));
                    assertTrue(getFrameColor(frames, 6, 0)
                        .equals(new Color(0, 0, 0)));
                    assertTrue(getFrameColor(frames, 7, 0)
                        .equals(new Color(50, 50, 50)));
                
            });

            it("should return blink animation with multiple sections", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new SectionBuilder()
                        .setStart(0)
                        .setEnd(0)
                        .setType(ActionType.BLINK)
                        .addColor(100, 100, 100)
                        .addProperty("transition", 2)
                        .build(),
                    new SectionBuilder()
                        .setStart(1)
                        .setEnd(1)
                        .setType(ActionType.BLINK)
                        .addColor(200, 200, 200)
                        .addProperty("transition", 1)
                        .build()
                    )
                ));

                assertEquals(frames.size(), 4);

                assertTrue(getFrameColor(frames, 0, 0)
                    .equals(new Color(0, 0, 0)));
                assertTrue(getFrameColor(frames, 1, 0)
                    .equals(new Color(50, 50, 50)));
                assertTrue(getFrameColor(frames, 2, 0)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 3, 0)
                    .equals(new Color(50, 50, 50)));

                assertTrue(getFrameColor(frames, 0, 1)
                    .equals(new Color(0, 0, 0)));
                assertTrue(getFrameColor(frames, 1, 1)
                    .equals(new Color(200, 200, 200)));
                assertTrue(getFrameColor(frames, 2, 1)
                    .equals(new Color(0, 0, 0)));
                assertTrue(getFrameColor(frames, 3, 1)
                    .equals(new Color(200, 200, 200)));

            });

        });

        describe("Wave type", () -> {

            it("should return four frames", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new SectionBuilder()
                        .setStart(0)
                        .setEnd(3)
                        .addSubsection(0, 1)
                        .addSubsection(3)
                        .setType(ActionType.WAVE)
                        .addColor(100, 100, 100)
                        .addProperty("speed", 4)
                        .addProperty("length", 2)
                        .build()
                    )
                ));

                assertEquals(frames.size(), 4);

                assertTrue(getFrameColor(frames, 0, 0)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 1, 0)
                    .equals(new Color(50, 50, 50)));
                assertTrue(getFrameColor(frames, 2, 0)
                    .equals(new Color(0, 0, 0)));
                assertTrue(getFrameColor(frames, 3, 0)
                    .equals(new Color(50, 50, 50)));
                assertTrue(getFrameColor(frames, 0, 1)
                    .equals(new Color(50, 50, 50)));
                assertTrue(getFrameColor(frames, 1, 1)
                    .equals(new Color(0, 0, 0)));
                assertTrue(getFrameColor(frames, 2, 1)
                    .equals(new Color(50, 50, 50)));
                assertTrue(getFrameColor(frames, 3, 1)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 0, 2) == null);
                assertTrue(getFrameColor(frames, 1, 2) == null);
                assertTrue(getFrameColor(frames, 2, 2) == null);
                assertTrue(getFrameColor(frames, 3, 2) == null);
                assertTrue(getFrameColor(frames, 0, 3)
                    .equals(new Color(50, 50, 50)));
                assertTrue(getFrameColor(frames, 1, 3)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 2, 3)
                    .equals(new Color(50, 50, 50)));
                assertTrue(getFrameColor(frames, 3, 3)
                    .equals(new Color(0, 0, 0)));

            });

        });

        describe("Mixed types", () -> {

            it("should return correct frames with blink and static types combined", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new SectionBuilder()
                        .setStart(0)
                        .setEnd(0)
                        .setType(ActionType.BLINK)
                        .addColor(100, 100, 100)
                        .addProperty("transition", 2)
                        .build(),
                    new SectionBuilder()
                        .setStart(1)
                        .setEnd(1)
                        .setType(ActionType.STATIC)
                        .addColor(200, 200, 200)
                        .build()
                    )
                ));

                assertEquals(frames.size(), 4);

                assertTrue(getFrameColor(frames, 0, 0)
                    .equals(new Color(0, 0, 0)));
                assertTrue(getFrameColor(frames, 1, 0)
                    .equals(new Color(50, 50, 50)));
                assertTrue(getFrameColor(frames, 2, 0)
                    .equals(new Color(100, 100, 100)));
                assertTrue(getFrameColor(frames, 3, 0)
                    .equals(new Color(50, 50, 50)));

                assertTrue(getFrameColor(frames, 0, 1)
                    .equals(new Color(200, 200, 200)));
                assertTrue(getFrameColor(frames, 1, 1)
                    .equals(new Color(200, 200, 200)));
                assertTrue(getFrameColor(frames, 2, 1)
                    .equals(new Color(200, 200, 200)));
                assertTrue(getFrameColor(frames, 3, 1)
                    .equals(new Color(200, 200, 200)));

            });

        });
    }

    /* * * * * * * * * *
     *     Helpers     *
     * * * * * * * * * */

    ActionRequest request(List<Section> sections) {
        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setSections(sections);
        return actionRequest;
    }

    Color getFrameColor(List<Frame> frames, int frameIndex, int colorIndex) {
        List<Color> colors = frames.get(frameIndex).getColors();
        return colors.size() > colorIndex ? colors.get(colorIndex) : null;
    }

}
