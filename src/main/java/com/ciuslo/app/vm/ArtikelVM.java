/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ciuslo.app.vm;



import com.ciuslo.app.entity.Artikel;
import com.ciuslo.app.model.ArtikelDao;
import com.ciuslo.app.model.ArtikelService;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.template.freemarker.FreeMarkerRoute;

/**
 *
 * @author HP
 */
public class ArtikelVM {
    public static ArtikelService<Artikel> articleDbService = new ArtikelDao<Artikel>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public void action(final String ipAddress, final int portAddress){
        get(new FreeMarkerRoute("/") {
            @Override
            public ModelAndView handle(Request request, Response response) {
                Map<String, Object> viewObjects = new HashMap<String, Object>();
                ArrayList<Artikel> articles = articleDbService.readAll();
                viewObjects.put("hostPort", ipAddress+":"+portAddress);
                
                if (articles.isEmpty()) {
                    viewObjects.put("hasNoArticles", "Welcome, please click \"Write Article\" to begin.");
                } else {
                    Deque<Artikel> showArticles = new ArrayDeque<Artikel>();

                    for (Artikel article : articles) {
                        if (article.readable()) {
                            showArticles.addFirst(article);
                        }
                    }

                    viewObjects.put("articles", showArticles);  
                }

                viewObjects.put("templateName", "articleList.html");

                return modelAndView(viewObjects, "layout.html");
            }
        });

        get(new FreeMarkerRoute("/article/create") {
            @Override
            public Object handle(Request request, Response response) {
                Map<String, Object> viewObjects = new HashMap<String, Object>();
                viewObjects.put("hostPort", ipAddress+":"+portAddress);
                viewObjects.put("templateName", "articleForm.html");

                return modelAndView(viewObjects, "layout.html");
            }
        });

        post(new Route("/article/create") {
            @Override
            public Object handle(Request request, Response response) {
                String title = request.queryParams("article-title");
                String summary = request.queryParams("article-summary");
                String content = request.queryParams("article-content");
                logger.info("judul :"+title);
                Artikel article = new Artikel(title, summary, content, articleDbService.readAll().size());

                articleDbService.create(article);

                response.status(201);
                response.redirect("/");
                return "";
            }
        });

        get(new FreeMarkerRoute("/article/read/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                Map<String, Object> viewObjects = new HashMap<String, Object>();

                viewObjects.put("hostPort", ipAddress+":"+portAddress);
                viewObjects.put("templateName", "articleRead.html");

                viewObjects.put("article", articleDbService.readOne(id));

                return modelAndView(viewObjects, "layout.html");
            }
        });

        get(new FreeMarkerRoute("/article/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                Map<String, Object> viewObjects = new HashMap<String, Object>();

                viewObjects.put("hostPort", ipAddress+":"+portAddress);
                viewObjects.put("templateName", "articleForm.html");

                viewObjects.put("article", articleDbService.readOne(id));

                return modelAndView(viewObjects, "layout.html");
            }
        });

        post(new Route("/article/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id      = Integer.parseInt(request.queryParams("article-id"));
                String title    = request.queryParams("article-title");
                String summary  = request.queryParams("article-summary");
                String content  = request.queryParams("article-content");

                articleDbService.update(id, title, summary, content);

                response.status(200);
                response.redirect("/");
                return "";
            }
        });

        get(new Route("/article/delete/:id") {
            @Override
            public Object handle(Request request, Response response) {

                Integer id = Integer.parseInt(request.params(":id"));

                articleDbService.delete(id);

                response.status(200);
                response.redirect("/");
                return "";
            }
        });
    }
}
