window.addEventListener('load', (event) => {

    let h1 = document.getElementById('h1emp');
    h1.innerText = `Welcome to the manager page: ${localStorage.getItem('username')}`;
    populateReimbursementTable();
});


let logout = document.querySelector('#logout-btn');

logout.addEventListener('click', () => {

    localStorage.removeItem('jwt');
    window.location = '/index.html';
});



let clearBtn = document.querySelector('#clear-btn');

clearBtn.addEventListener('click', () => {

    var filter, table, tr, td, i, txtValue;
    filter = ""
    table = document.querySelector("#reimbursements-tbl");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[7];
        if (td) {
            txtValue = td.innerText;
            if (txtValue.indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
});

let pendingBtn = document.querySelector('#pending-btn');

pendingBtn.addEventListener('click', () => {

    var filter, table, tr, td, i, txtValue;
    filter = "pending"
    table = document.querySelector("#reimbursements-tbl");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[7];
        if (td) {
            txtValue = td.innerText;
            if (txtValue.indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
});


let approvedBtn = document.querySelector('#approved-btn');

approvedBtn.addEventListener('click', () => {

    var filter, table, tr, td, i, txtValue;
    filter = "approved"
    table = document.querySelector("#reimbursements-tbl");
    console.log(filter);
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[7];
        if (td) {
            txtValue = td.innerText;
            if (txtValue.indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
});

let deniedBtn = document.querySelector('#denied-btn');

deniedBtn.addEventListener('click', () => {

    var filter, table, tr, td, i, txtValue;
    filter = "denied"
    table = document.querySelector("#reimbursements-tbl");
    console.log(filter);
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[7];
        if (td) {
            txtValue = td.innerText;
            if (txtValue.indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
});


async function populateReimbursementTable() {
    const URL = 'http://34.83.66.148:1000/reimbursements';

    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })

    if (res.status === 200) {
        let reimbursemnts = await res.json();
        document.querySelector('#reimbursements-tbl > tbody').innerHTML = '';


        let tbody = document.querySelector('#reimbursements-tbl > tbody');

        for (let reimbursemnt of reimbursemnts) {
            let tr = document.createElement('tr');

            let td1 = document.createElement('td');
            td1.innerText = reimbursemnt.id;
            td1.className = 'has-text-centered is-vcentered';

            let td2 = document.createElement('td');
            td2.innerText = reimbursemnt.amount;
            td2.className = 'has-text-centered is-vcentered';

            let td3 = document.createElement('td');
            td3.innerText = reimbursemnt.dateSubmitted;
            td3.className = 'has-text-centered is-vcentered';

            let td4 = document.createElement('td');
            td4.innerText = (reimbursemnt.managerUsername ? reimbursemnt.dateResolved : 'Not reviewed');
            td4.style.color = (reimbursemnt.managerUsername ? td4.style.color : 'red');
            td4.className = 'has-text-centered is-vcentered';

            let td5 = document.createElement('td');
            td5.innerText = reimbursemnt.description;
            td5.className = 'has-text-centered is-vcentered';

            let td6 = document.createElement('td');
            td6.innerText = reimbursemnt.employeeUsername;
            td6.className = 'has-text-centered is-vcentered';

            let td7 = document.createElement('td');
            td7.innerText = (reimbursemnt.managerUsername ? reimbursemnt.managerUsername : 'Not assigned')
            td7.style.color = (reimbursemnt.managerUsername ? td7.style.color : 'red');
            td7.className = 'has-text-centered is-vcentered';

            let td8 = document.createElement('td');
            td8.innerText = reimbursemnt.statusName;
            td8.className = 'has-text-centered is-vcentered';

            let td9 = document.createElement('td');
            td9.innerText = reimbursemnt.typeName;
            td9.className = 'has-text-centered is-vcentered';

            let td10 = document.createElement('td');
            td10.className = 'has-text-centered is-vcentered';


            let res2 = await fetch(`http://34.83.66.148:1000/reimbursements/${reimbursemnt.id}/image`, {
                method: 'GET'
            })
            if (res2.status === 200) {
                let imgElement = document.createElement('img');
                imgElement.setAttribute('src', `http://34.83.66.148:1000/reimbursements/${reimbursemnt.id}/image`);
                imgElement.style.height = '200px';
                td10.appendChild(imgElement);
            } else {
                //td10.innerText = 'not image';
                let errormsg1 = await res2.text(); // to get the error message from the backend
                td10.innerText = errormsg1;
                td10.style.color = 'red';
            }

            //imgElement.style.height = '200px';
            //td10.appendChild(imgElement);


            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            tr.appendChild(td7);
            tr.appendChild(td8);
            tr.appendChild(td9);
            tr.appendChild(td10);

            //cheking if the reimbursement
            if (!reimbursemnt.managerUsername) {
                let statusInput = document.createElement('input');
                statusInput.setAttribute('type', 'number');
                statusInput.setAttribute('min', 1);
                statusInput.setAttribute('max', 4);
                statusInput.className = 'input';

                let updateStatusBtn = document.createElement("button");
                updateStatusBtn.className = 'button is-link'
                updateStatusBtn.innerText = 'Update Status';
                //updateStatusBtn.className = 'has-text-centered is-vcentered';

                updateStatusBtn.addEventListener('click', async() => {
                    let status = statusInput.value;

                    try {
                        let res = await fetch(`http://34.83.66.148:1000/reimbursements/${reimbursemnt.id}?statusId=${status}`, {
                            method: 'PATCH',
                            headers: {
                                Authorization: `Bearer ${localStorage.getItem('jwt')}`
                            }
                        });



                        populateReimbursementTable(); //to refresh the table once it update it


                    } catch (e) {
                        console.log(e);
                    }

                });

                tr.appendChild(statusInput);
                tr.appendChild(updateStatusBtn);

            }



            tbody.appendChild(tr);
        }
    }




}