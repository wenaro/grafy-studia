package edu.graphs.controller;

import edu.graphs.input.Input;
import edu.graphs.model.Graph;
import edu.graphs.service.GraphService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class GenerateController {

    private final GraphService graphService;

    @GetMapping("/generate")
    public String generate(final Model model) {
        model.addAttribute("input", new Input());
        return "generate";
    }

    @PostMapping(value = "/generate")
    public String submitInput(final Model model, @ModelAttribute final Input input) {
        final Graph graph = graphService.createGraph(input.getType(), input.getText());
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        return "generate";
    }
}
