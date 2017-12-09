package com.desertive.firefly.core.services;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.greghaskins.spectrum.Spectrum;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import static com.greghaskins.spectrum.dsl.specification.Specification.describe;
import static com.greghaskins.spectrum.dsl.specification.Specification.it;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(Spectrum.class)
@SpringBootTest
@ActiveProfiles("test")
public class FrameServiceTest {

    /* * * * * * * * * * * * * * *
     * Rules and class variables *
     * * * * * * * * * * * * * * */

    @ClassRule
    public static final SpringClassRule classRule = new SpringClassRule();

    @Rule
    public SpringMethodRule methodRule = new SpringMethodRule();

    @Autowired
    FrameService frameService;

    /* * * * * * * * * *
     *      Tests      *
     * * * * * * * * * */

    {
        describe("Frame merger", () -> {

            it("should merge two frame lists into one", () -> {

                List<Frame> listOne = Arrays.asList(
                    new Frame(Arrays.asList(null, new Color(2,2,2)))
                );
                List<Frame> listTwo = Arrays.asList(
                    new Frame(Arrays.asList(new Color(3,3,3))),
                    new Frame(Arrays.asList(new Color(3,3,3)))
                );
                List<Frame> frames = frameService.mergeFrameLists(listOne, listTwo);
                assertTrue(frames.get(0).getColors().get(0).equals(new Color(3,3,3)));
                assertTrue(frames.get(0).getColors().get(1).equals(new Color(2,2,2)));
                assertTrue(frames.get(1).getColors().get(0).equals(new Color(3,3,3)));
                assertTrue(frames.get(1).getColors().get(1).equals(new Color(2,2,2)));

            });

        });

        describe("Step converter", () -> {

            it("should create frames from transition steps", () -> {

                List<Frame> frames = frameService.convertStepsIntoFrames(Arrays.asList(
                    new TransitionStep(Arrays.asList(new Color(6,6,6)), 1, 1),
                    new TransitionStep(Arrays.asList(new Color(2,0,2)), 3, 1),
                    new TransitionStep(Arrays.asList(new Color(6,8,6)), 0, 1),
                    new TransitionStep(Arrays.asList(new Color(100,50,100)), 1, 1)
                ));
                assertEquals(frames.size(), 9);
                assertTrue(frames.get(0).getColors().get(0).equals(new Color(6,6,6)));
                assertTrue(frames.get(1).getColors().get(0).equals(new Color(4,3,4)));
                assertTrue(frames.get(2).getColors().get(0).equals(new Color(2,0,2)));
                assertTrue(frames.get(3).getColors().get(0).equals(new Color(3,2,3)));
                assertTrue(frames.get(4).getColors().get(0).equals(new Color(4,4,4)));
                assertTrue(frames.get(5).getColors().get(0).equals(new Color(5,6,5)));
                assertTrue(frames.get(6).getColors().get(0).equals(new Color(6,8,6)));
                assertTrue(frames.get(7).getColors().get(0).equals(new Color(100,50,100)));
                assertTrue(frames.get(8).getColors().get(0).equals(new Color(53,28,53)));

            });

        });

    }
}
