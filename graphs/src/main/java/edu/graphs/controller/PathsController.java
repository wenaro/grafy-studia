package edu.graphs.controller;

import edu.graphs.input.VertexInput;
import edu.graphs.model.GraphKeeper;
import edu.graphs.model.Vertex;
import edu.graphs.service.GraphService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PathsController {

    @Autowired
    private GraphService graphService;

    @GetMapping("/paths")
    public String get(final Model model) {
        addModelAttributes(model, new VertexInput(), "");
        return "paths";
    }

    @PostMapping(value = "/paths",
                 params = "action=find")
    public String submitInput(final Model model, @ModelAttribute final VertexInput vertexInput) {
        final Vertex vertex = graphService.findVertexById(GraphKeeper.graph, vertexInput.getId());

        final List<String> paths = graphService.getTheShortestPathsToEachVertexFromSelected(GraphKeeper.graph, vertex);

        final StringBuilder pathsSB = new StringBuilder();
        for (final String path : paths) {
            pathsSB.append(path).append("\n");
        }
        addModelAttributes(model, new VertexInput(), pathsSB.toString());
        System.out.println(pathsSB.toString());

        return "paths";
    }

    private void addModelAttributes(final Model model, final VertexInput vertexInput, final String paths) {
        model.addAttribute("vertexInput", vertexInput);
        model.addAttribute("vertices", GraphKeeper.graph.getVertices());
        model.addAttribute("edges", GraphKeeper.graph.getEdges());
        model.addAttribute("paths", paths);
    }
}
