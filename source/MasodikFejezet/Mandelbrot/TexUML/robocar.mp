input metauml;

beginfig(1);

	Class.A("MyShmClient")
		("# nr_graph: NodeRefGraph*", "# m_teamname: string", "- nr2v: map<unsigned_object_id_type, NRGVertex>")
		("+MyShmClient()",
		"+~MyShmClient()",
		"+start()",
		"+start10()",
		"+num_vertices()",
		"+print_edges()",
		"+print_vertices()",
		"+bgl_graph()",
		"+hasDijkstraPath()",
		"+hasBellmanFordPath()",
		"-foo()",
		"-init()",
		"-gangsters()",
		"-initcops()",
		"-pos()",
		"-car()",
		"-route()");

	Class.B("SmartCar")
		("+ id: int", 
		"+ from: unsigned", 
		"+ to: unsigned", 
		"+ step: int")
		();

	Class.C("Gangster")
		()
		();


	Class.D("Cop")
		()
		();


	Class.E("ShmClient")
		("# shm_map: boost.interprocess.offset_ptr<justine.robocar.shm_map_Type>",
		"- segment: boost.interprocess.managed_shared_memory*")
		("+ ShmClient()",
		"+ ~ShmClient()",
		"+ start()",
		"+ get_random_node()",
		"+ num_edges",
		"+ alist()",
		"+ alist_inv()",
		"+salist()",
		"+ set_salist()",
		"+ palist()",
		"+ hasNode()",
		"+ dst()",
		"+ dst()",
		"+ toGPS()",
		"+ toGPS()",
		"- foo()",
		"- init()",
		"- gangsters()",
		"- pos()",
		"- car()",
		"- route1()",
		"- route2()");

	Class.F("OSMReader")
		("+onewayc: int",
		 "+onewayf: int",
		 "#nOSM_nodes: int",
		 "#nOSM_ways: int",
		 "#nOSM_relations: int",
		 "#sum_unique_highhway_nodes: int",
		 "#sum_highhway_nodes: int",
		 "#sum_highhway_length: int",
		 "#edge_multiplicity: int",
		 "#nbuses: int",
		 "#max_edge_length: double",
		 "#mean_edge_lenght: double",
		 "#cedges: int",
		 "#locations: OSMLocations",
		 "-m_estimator: size_t",
		 "-alist: AdjacencyList&",
		 "-palist: AdjacencyList&",
		 "-waynode_locations: WaynodeLocations&",
		 "-busWayNodesMap: WayNodesMap&",
		 "-way2nodes: Way2Nodes&",
		 "-way2name: WayNames&")
		 ("+OSMreader()",
		  "+~OSMReader()",
		  "+get_estimated_memory()",
		  "+edge()",
		  "+node()",
		  "+way()",
		  "+relation",
		  "-distance()");
	

	Class.G("CarLexer")
		 	("-m_cmd: int",
		 	 "-name: char[128]",
		 	 "-num: int",
		 	 "-role: char",
		 	 "-m_errnumber: int",
		 	 "-m_guided: bool",
		 	 "-route: vector<unsigned int>",
		 	 "-m_id: int",
		 	 "-from: unsigned int",
		 	 "-to: unsigned int")
		 	 ("+yylex()",
		 	  "+get_name()",
		 	  "+get_num()",
		 	  "+get_errnumber()",
		 	  "+get_guided()",
		 	  "+get_cmd()",
		 	  "+get_id()",
		 	  "+get_route()",
		 	  "+get_from()",
		 	  "get_to()");



	Class.H("SmartCity")
		("# segment: managed_shared_memory*",
		"# shm_map: offset_ptr<shm_map_Type>",
		"# m_delay: int",
		"# m_run: bool",
		"- m_mutex: mutex",
		"- m_cv: condition_variable",
		"- m_thread: thread",
		"- m_waynode_locations: WaynodeLocations",
		"- m_busWayNodesMap: WayNodesMap",
		"- m_way2nodes: Way2Nodes",
		"- m_way2name: WayNames",
		"- m_remover: shm_remove*")
		("+ SmartCity()",
		"+ SmartCity()",
		"+ ~SmartCity()",
		"+ node2way()",
		"+ processes()",
		"+ city_run()",
		"+ busWayLength()");



	Class.I("Traffic")
		("#segment:managed_shared_memory",
		"#shm_map:offset_ptr<shm_map_Type>",
		"#m_delay:int",
		"#m_run:bool",
		"#m_catchdist:double",
		"-m_size:int",
		"-m_time:int",
		"-m_minutes:int",
		"-m_mutex:mutex",
		"-m_cv:condition_variable",
		"-m_thread:thread",
		"-cars:vector<shared_ptr<Car>>",
		"-m_smart_cars:vector<shared_ptr<SmartCar>>",
		"-m_cop_cars:vector<shared_ptr<CopCar>>",
		"-m_smart_cars_map:map<int, shared_ptr<SmartCar>>",
		"-cars_mutex:mutex",
		"-m_type:TrafficType",
		"-logFile:fstream*",
		"-logfile:string")

		("+Traffic()",
		"+~Traffic()",
		"+processes()",
		"+get_title()",
		"+node()",
		"+traffic_run",
		"+steps()",
		"+pursuite()",
		"+nedges()",
		"+alist()",
		"+alist_inv",
		"+salist()",
		"+palist()",
		"+hashNode()",
		"+start_server()",
		"+cmd_session()",
		"+naive_node_for_nearest_gengster()",
		"+dst()",
		"+dst()",
		"+toGPS()",
		"+naive_nearest_gangster()",
		"+get_type()",
		"+get_time()",
		"-addCop",
		"-addGangster()");

	
	

	Class.J("SharedData")
		("+m_alist: uint_vector",
		"+m_salist: uint_vector",
		"+m_palist: uint_vector",
		"+lon: int",
		"+lat: int")
		("+SharedData()");


	Class.K("Car")
			("#traffic: Traffic&",
			 "#m_type: CarType",
			 "#m_from: unsigned_object_id_type",
			 "#m_to: unsigned_object_id_type",
			 "#m_step: unsigned_object_id_type")
			 ("+Car()",
			  "+init()",
			  "+step()",
			  "+from()",
			  "+to()",
			  "+get_step()",
			  "+get_type()",
			  "+set_type()",
			  "+to_node()",
			  "+get_max_steps()",
			  "+nextEdge()",
			  "+nextSmarterEdge",
			  "+print()");
	


	Class.L("CarType")
		("NORMAL",
		"POLICE",
		"GANGSTER",
		"CAUGHT")
		();

	
	Class.M("SmartCar")
		("- m_guided: bool",
		"- m_routed: bool",
		"- route: vector<unsigned int>")
		("+ SmartCar()",
		"+ step()",
		"+ init()",
		"+ print()",
		"+ get_guided()",
		"+ set_route()",
		"+ nextEdge()",
		"+ nextGuidedEdge()",
		"+ set_fromto()");

	Class.N("AntCar")
		("+alist: AdjacencyList",
		"+alist evaporate: AdjacencyList",
		"-rnd: bool")
		("+AntCar()",
			"+nextSmarterEdge()",
			"+print()",
			"+ant",
			"+ant_rnd()",
			"+ant_rernd()",
			"+ant_mrernd()");


	Class.O("CopCar")
			("#m_num_captured_gangsters: int",
			 "#m_name: string")
			("+CopCar()",
			 "+print()",
			 "+get_name()",
			 "+get_num_captured_gangsters()",
			 "+captured_gangster()");


	Class.P("TrafficType")
		("NORMAL",
		"ANT",
		"ANT_RND",
		"ANT_RERND",
		"ANT_MRERND")
		();

	Class.T("shm_remove")
		("+ name: char const*")
		("+ shm_remove()",
		"+ ~shm_remove()");
	
	Class.V("GLOBAL")
	()
	("+ main()");


classStereotypes.B("<<Struct>>");
classStereotypes.C("<<Typedef>>");
classStereotypes.D("<<Typedef>>");
classStereotypes.L("<<enumeration>>");
classStereotypes.P("<<enumeration>>");
classStereotypes.T("<<Struct>>");

Package.Q("sampleclient")(A, B, C, D, E);

Package.R("robocar")(J, H, L, K, M, O, N, I, P, F, G);

Package.S("justine")(Q, R);

topToBottom.midx(20)(C, A, B);
leftToRight(20)(D, A);
leftToRight(20)(A, E);

J.w = H.e + (100, 0);
J.e = L.w + (-100, 0);
topToBottom.midx(50)(L, K, M);
leftToRight.midy(50)(O, M); 
leftToRight.midy(100)(N, K);
leftToRight.midy(50)(K, I);
topToBottom.midx(50)(I, P);
topToBottom.midx(100)(F, J);
leftToRight.midy(100)(G, F);
leftToRight.midy(100)(S, T);
topToBottom.midx(50)(S, V);

topToBottom(20)(Q, R);

Class_noVisibilityMarkers.L;
Class_noVisibilityMarkers.P;

drawObjects(S, T, V);
draw N.attributeStack.pict[0].sw -- N.attributeStack.pict[0].se ;
draw N.attributeStack.pict[1].sw -- N.attributeStack.pict[1].se ;




link(nest)(B.n -- A.s);
link(nest)(C.s -- A.n);
link(nest)(D.e -- A.w);
link(inheritance)(A.e -- E.w);


link(inheritance)(N.e -- K.w);
link(inheritance)(M.n -- K.s);
link(inheritance)(O.e -- M.w);
link(association)(L.s -- K.n);
link(association)(K.e -- I.w);
link(association)(I.s -- P.n);


endfig;

end