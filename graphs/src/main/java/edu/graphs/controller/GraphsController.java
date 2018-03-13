package edu.graphs.controller;

import edu.graphs.input.Input;
import edu.graphs.service.GraphService;
import lombok.AllArgsConstructor;
import org.jgrapht.Graph;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class GraphsController {

    private final GraphService graphService;

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("input", new Input());
        return "index";
    }

    @PostMapping(value = "/")
    public String submitInput(final Model model, @ModelAttribute final Input input) {
        final Graph graph = graphService.convertNeighborhoodMatrix(input.getText());
        model.addAttribute("vertexSet", graph.vertexSet());
        model.addAttribute("edgeSet", graph.edgeSet());
        return "index";

    }

}
