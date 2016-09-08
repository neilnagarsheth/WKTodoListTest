var TodoListWrapper = React.createClass({
  getInitialState: function(){
    return {data: []};
  },
  updateData: function(value){
	  this.setState({data: value});
  },
  componentDidMount: function(){
    this.serverRequest = $.get(this.props.route.options.get, function(result){
    	this.setState({data: result});
    }.bind(this)).fail(function(error){
		alert("An Error Has Occured Retrieving the Todo Lists");
		console.error(error);
    });
  },
  render: function() {
    return (
      <div className="todoListWrapper">
        <h1>Todo Lists</h1>
        <AddItem data={this.state.data} updateData={this.updateData} options={this.props.route.options} />
        <Items data={this.state.data} updateData={this.updateData} options={this.props.route.options}/>
      </div>
    );
  }
});


var TodoItemWrapper = React.createClass({
  getInitialState: function(){
    return {data: []};
  },
  updateData: function(value){
	  this.setState({data: value});
  },
  componentDidMount: function(){
	var url = this.props.route.options.get + "/" + this.props.params.listId;
    this.serverRequest = $.get(url, function(result){
    	this.setState({data: result});
    }.bind(this)).fail(function(error){
		alert("An Error Has Occured Retrieving the Tasks for this Todo List");
		console.error(error);
    });;
  },
  render: function() {
	return (
      <div className="todoItemWrapper">
        <h1>{this.props.params.listName}</h1>
        <AddItem data={this.state.data} updateData={this.updateData} options={this.props.route.options} listId={this.props.params.listId}/>
        <Items data={this.state.data} updateData={this.updateData} options={this.props.route.options}/>
        <BackToHome/>
      </div>
    );
  }
});

var Items = React.createClass({
  render: function() {
    var stateData = this.props.data;
    var updateDataFunc = this.props.updateData;
    var options = this.props.options;
    var itemNodes = [];
    if(this.props.data && this.props.data.length){
    	if(options.type === "todos"){
    		itemNodes = this.props.data.map(function(item){
      	      return(
      	        <Todo item={item} key={item.listId} data={stateData} updateData={updateDataFunc} options={options} >
      	        </Todo>
          	   );
          	});
    	} else{
    		itemNodes = this.props.data.map(function(item){
      	      return(
      	        <Todo item={item} key={item.taskId} data={stateData} updateData={updateDataFunc} options={options} >
      	        </Todo>
          	   );
          	});
    	}
    }

    return (
      <div className="items">
      	{itemNodes}
      </div>
    );
  }
});

var Todo = React.createClass({
  render: function() {
	if(this.props.options.type === "todos"){
		return (
	      <div className="todo">
	        <li className="itemName">
	          <ReactRouter.Link to={"/list/"+this.props.item.listName+"/"+this.props.item.listId}>{this.props.item.listName}</ReactRouter.Link> <UpdateItem item={this.props.item} data={this.props.data} updateData={this.props.updateData} options={this.props.options}/>  <DeleteItem id={this.props.item.listId} data={this.props.data} updateData={this.props.updateData} options={this.props.options}/>
	        </li>
	        {this.props.children}
	      </div>
	    );
	} else{
		var doneStyle = {};
		if(this.props.item.isDone){
			doneStyle = {"textDecoration": "line-through"}
		}
		return (
	      <div className="todo">
	        <p className="itemName" style={doneStyle}>
	          <CheckItem item={this.props.item} data={this.props.data} updateData={this.props.updateData} options={this.props.options}/>  {this.props.item.taskName}  <UpdateItem item={this.props.item} data={this.props.data} updateData={this.props.updateData} options={this.props.options}/>  <DeleteItem item={this.props.item} data={this.props.data} updateData={this.props.updateData} options={this.props.options}/>
	        </p>
	        {this.props.children}
	      </div>
	    );
	}
  }
});

var AddItem = React.createClass({
	addItem: function(){
		var name  = prompt("Name");
		name = name ? name.trim() : name;
		if(name){
			var request = this.props.options.type === "todos" ? {listName: name} : {taskName: name, listId: this.props.listId};
			this.serverRequest = AjaxRequest(this.props.options.add, JSON.stringify(request), "POST", "json").done(function (response){
				var result = this.props.data && this.props.data.length ? this.props.data : [];
				result.push(response);
				this.props.updateData(result);
			}.bind(this)).fail(function(error){
				alert("An Error Has Occured Adding this item");
		    	console.error(error);
		    });
		} else{
			alert("Please Try to add an item again");
		}
	},
	render: function(){
		return(
		  <div className="addItem">
		    <button type="button" onClick={this.addItem}>New</button>
		  </div>
		);
	}
});

var DeleteItem = React.createClass({
	deleteItem: function(){
		var url = this.props.options.remove;
		url += this.props.options.type === "task" ? this.props.item.listId + "/" + this.props.item.taskId : this.props.id
		this.serverRequest = AjaxRequest(url, null, "DELETE", null).done(function (response){
			var id = this.props.id || this.props.item.taskId
			this.props.updateData(DeleteTodoTask(this.props.data, id));
		}.bind(this)).fail(function(error){
			alert("An Error Has Occured Processing this Delete");
	    	console.error(error);
	    }); 
	},
	render: function(){
		return(
		  <button className="deleteItem" type="button" onClick={this.deleteItem}>Remove</button>
		)
	}
})

var CheckItem = React.createClass({
	onChange: function(){
		this.props.item.isDone = !this.props.item.isDone;
		this.serverRequest = AjaxRequest(this.props.options.update, JSON.stringify(this.props.item), "PUT", "json").done(function (response){
			this.props.updateData(UpdateTodoTask(this.props.data, this.props.item));
		}.bind(this)).fail(function(error){
			alert("An Error Has Occured Checking off this item");
	    	console.error(error);
	    }); 
	},
	render: function(){
		return(
		  <input className="checkItem" type="checkbox" checked={this.props.item.isDone} onChange={this.onChange} />
		)
	}
})

var UpdateItem = React.createClass({
	updateItem: function(){
		var name = this.props.options.type === "task" ? this.props.item.taskName : this.props.item.listName;
		var newName = prompt("Name", name);
		newName = newName ? newName.trim() : newName;
		if(newName && newName.trim()){
			var url = this.props.options.update;
			var request;
			if(this.props.options.type === "task"){
				this.props.item.taskName = newName;
			} else {
				this.props.item.listName = newName;
			}
			request = this.props.item;
			this.serverRequest = AjaxRequest(this.props.options.update, JSON.stringify(request), "PUT", "json").done(function (response){
				this.props.updateData(UpdateTodoTask(this.props.data, request));
			}.bind(this)).fail(function(error){
				alert("An Error Has Occured Updating this item");
		    	console.error(error);
		    });
		} else{
			alert("Please Try to update task again");
		}
	},
	render: function(){
		return(
		  <button className="updateItem" type="button" onClick={this.updateItem}>Edit</button>			
		)
	}
})

function AjaxRequest(url, data, type, dataType){
	return $.ajax({
		url: url,
		type: type,
		data: data,
		contentType:"application/json",
		dataType: dataType,
	});
}

function UpdateTodoTask(result, item){
	var id = item.taskId || item.listId;
	for(var i = 0; i<result.length; i++){
		var resultId = result[i].taskId || result[i].listId;
		if(resultId === id){
			result[i] = item;
			return result;
		}
	}
}

function DeleteTodoTask(result, id){
	for(var i = 0; i<result.length; i++){
		var resultId = result[i].taskId || result[i].listId;
		if(resultId === id){
			result.splice(i,1);
			return result;
		}
	}
}

var BackToHome = React.createClass({
	render: function(){
		return(
		  <ReactRouter.Link to="/"><button className="backToHome" type="button">Home</button></ReactRouter.Link>
		)
	}

})

var todoLists = {"get":"/lists", "remove":"/removeList/", "add":"/addList", "update": "/updateList", "type":"todos"};
var tasks = {"get":"/taskList/", "add": "/addTask", "remove":"/removeTask/", "update": "/updateTask", "type": "task"};

ReactDOM.render(
	(<ReactRouter.Router>
    <ReactRouter.Route path="/" options= {todoLists} component={TodoListWrapper} ></ReactRouter.Route>
    <ReactRouter.Route path="/list/:listName/:listId" options= {tasks} component={TodoItemWrapper} ></ReactRouter.Route>
    </ReactRouter.Router>), document.getElementById('content'));
