INSERT INTO project(name) VALUES('Projekt 1');

INSERT INTO task(name, description, budget, done, project_id) VALUES('Task 1', 'Description of task 1', 100.00, 1, SELECT id FROM project WHERE name = 'Projekt 1');
INSERT INTO task(name, description, budget, done, project_id) VALUES('Task 2', 'Description of task 2 Do', 100.00, 1, SELECT id FROM project WHERE name = 'Projekt 1');
INSERT INTO task(name, description, budget, done, project_id) VALUES('Task 3', 'Description of task 3 Do', 50.00, 0, SELECT id FROM project WHERE name = 'Projekt 1');
