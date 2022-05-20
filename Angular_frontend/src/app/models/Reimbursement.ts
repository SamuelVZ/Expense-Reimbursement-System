export interface Reimbursement{
  id: number;
  amount: number;
  dateSubmitted: string;
  dateResolved: string;
  description: string;
  employeeUsername: string;
  managerUsername: string;
  statusName: string;
  typeName: string;
}
