package com.desertive.firefly.core.client;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import com.desertive.firefly.core.data.builders.LedStripSectionBuilder;
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
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;
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
                    new LedStripSectionBuilder()
                        .setStart(0)
                        .setEnd(3)
                        .setType(ActionType.STATIC)
                        .setProperty("r", 100)
                        .setProperty("g", 200)
                        .setProperty("b", 150)
                        .build()
                    )
                ));

                assertEquals(frames.size(), 1);

            });

            it("should return different colors for different sections", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new LedStripSectionBuilder()
                        .setStart(0)
                        .setEnd(1)
                        .setType(ActionType.STATIC)
                        .setProperty("r", 242)
                        .setProperty("g", 194)
                        .setProperty("b", 8)
                        .build(),
                    new LedStripSectionBuilder()
                        .setStart(2)
                        .setEnd(5)
                        .setType(ActionType.STATIC)
                        .setProperty("r", 51)
                        .setProperty("g", 46)
                        .setProperty("b", 64)
                        .build(),
                    new LedStripSectionBuilder()
                        .setStart(6)
                        .setEnd(11)
                        .setType(ActionType.STATIC)
                        .setProperty("r", 1)
                        .setProperty("g", 218)
                        .setProperty("b", 53)
                        .build()
                    )
                ));

                assertTrue(getFrameColor(frames, 0, 0)
                    .equals(new Color(242, 194, 8)));
                assertTrue(getFrameColor(frames, 0, 1)
                    .equals(new Color(242, 194, 8)));
                assertTrue(getFrameColor(frames, 0, 2)
                    .equals(new Color(51, 46, 64)));
                assertTrue(getFrameColor(frames, 0, 5)
                    .equals(new Color(51, 46, 64)));
                assertTrue(getFrameColor(frames, 0, 6)
                    .equals(new Color(1, 218, 53)));
                assertTrue(getFrameColor(frames, 0, 11)
                    .equals(new Color(1, 218, 53)));

            });

        });

        describe("Blink type", () -> {

            it("should return blink animation with one section", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new LedStripSectionBuilder()
                        .setStart(0)
                        .setEnd(0)
                        .setType(ActionType.BLINK)
                        .setProperty("r", 100)
                        .setProperty("g", 50)
                        .setProperty("b", 200)
                        .setProperty("interval", 8)
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

            it("should return blink animation with multiple sections", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new LedStripSectionBuilder()
                        .setStart(0)
                        .setEnd(0)
                        .setType(ActionType.BLINK)
                        .setProperty("r", 100)
                        .setProperty("g", 100)
                        .setProperty("b", 100)
                        .setProperty("interval", 4)
                        .build(),
                    new LedStripSectionBuilder()
                        .setStart(1)
                        .setEnd(1)
                        .setType(ActionType.BLINK)
                        .setProperty("r", 200)
                        .setProperty("g", 200)
                        .setProperty("b", 200)
                        .setProperty("interval", 2)
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

        describe("Mixed types", () -> {

            it("should return correct frames with blink and static types combined", () -> {

                List<Frame> frames = fireflyClient.process(request(Arrays.asList(
                    new LedStripSectionBuilder()
                        .setStart(0)
                        .setEnd(0)
                        .setType(ActionType.BLINK)
                        .setProperty("r", 100)
                        .setProperty("g", 100)
                        .setProperty("b", 100)
                        .setProperty("interval", 4)
                        .build(),
                    new LedStripSectionBuilder()
                        .setStart(1)
                        .setEnd(1)
                        .setType(ActionType.STATIC)
                        .setProperty("r", 200)
                        .setProperty("g", 200)
                        .setProperty("b", 200)
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

    ActionRequest request(List<LedStripSection> ledStripSections) {
        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setLedStripSections(ledStripSections);
        return actionRequest;
    }

    Color getFrameColor(List<Frame> frames, int frameIndex, int colorIndex) {
        return frames.get(frameIndex).getLeds().get(colorIndex);
    }

}
